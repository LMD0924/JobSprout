package com.jobsprout.resume.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.jobsprout.resume.dto.ResumeDTO;
import com.jobsprout.resume.entity.Resume;
import com.jobsprout.resume.mapper.ResumeMapper;
import com.jobsprout.resume.service.ResumeService;
import com.jobsprout.resume.vo.ResumeVO;
import com.jobsprout.common.exception.BusinessException;
import com.jobsprout.common.result.ResultCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** 简历业务：每个学生只保留一份当前简历。 */
@Service
@RequiredArgsConstructor
public class ResumeServiceImpl implements ResumeService {
    private final ResumeMapper resumeMapper;

    @Override
    public ResumeVO getMyResume(Long userId) {
        Resume resume = resumeMapper.selectOne(new com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<Resume>()
                .eq(Resume::getUserId, userId).orderByDesc(Resume::getIsDefault).orderByDesc(Resume::getId).last("LIMIT 1"));
        return ResumeVO.from(resume);
    }

    @Override
    public ResumeVO getById(Long userId, Long id) {
        Resume resume = ownedResume(userId, id);
        return ResumeVO.from(resume);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResumeVO create(Long userId, ResumeDTO request) {
        Resume old = resumeMapper.selectOne(new com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<Resume>()
                .eq(Resume::getUserId, userId).last("LIMIT 1"));
        if (old != null) {
            throw new BusinessException(ResultCode.BAD_REQUEST, "你已经创建过简历，请直接编辑现有简历");
        }
        Resume resume = new Resume();
        BeanUtils.copyProperties(request, resume);
        resume.setUserId(userId);
        resume.setIsDefault(1);
        resume.setDeleted(0);
        if (resumeMapper.insert(resume) != 1) {
            throw new BusinessException(ResultCode.INTERNAL_ERROR, "简历创建失败");
        }
        return ResumeVO.from(resume);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResumeVO update(Long userId, Long id, ResumeDTO request) {
        Resume resume = ownedResume(userId, id);
        BeanUtils.copyProperties(request, resume);
        resume.setId(id);
        resume.setUserId(userId);
        resumeMapper.updateById(resume);
        return ResumeVO.from(resumeMapper.selectById(id));
    }

    @Override
    public void delete(Long userId, Long id) {
        ownedResume(userId, id);
        resumeMapper.delete(new LambdaUpdateWrapper<Resume>().eq(Resume::getId, id).eq(Resume::getUserId, userId));
    }

    private Resume ownedResume(Long userId, Long id) {
        Resume resume = resumeMapper.selectOne(new com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<Resume>()
                .eq(Resume::getId, id).eq(Resume::getUserId, userId));
        if (resume == null) throw new BusinessException(ResultCode.NOT_FOUND, "简历不存在");
        return resume;
    }
}

