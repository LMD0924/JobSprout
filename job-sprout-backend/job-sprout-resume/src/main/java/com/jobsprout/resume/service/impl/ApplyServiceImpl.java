package com.jobsprout.resume.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jobsprout.common.exception.BusinessException;
import com.jobsprout.common.result.ResultCode;
import com.jobsprout.resume.dto.ApplyDTO;
import com.jobsprout.resume.dto.ApplyStatusDTO;
import com.jobsprout.resume.entity.JobApply;
import com.jobsprout.resume.entity.Resume;
import com.jobsprout.resume.mapper.JobApplyMapper;
import com.jobsprout.resume.mapper.ResumeMapper;
import com.jobsprout.resume.service.ApplyService;
import com.jobsprout.resume.vo.ApplyRecordVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/** 投递业务，所有权校验在服务层完成，避免只依赖前端权限控制。 */
@Service
@RequiredArgsConstructor
public class ApplyServiceImpl implements ApplyService {
    private final JobApplyMapper applyMapper;
    private final ResumeMapper resumeMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ApplyRecordVO apply(Long userId, ApplyDTO request) {
        Resume resume = resumeMapper.selectOne(new LambdaQueryWrapper<Resume>()
                .eq(Resume::getId, request.getResumeId()).eq(Resume::getUserId, userId));
        if (resume == null) throw new BusinessException(ResultCode.NOT_FOUND, "简历不存在或不属于当前用户");
        if (applyMapper.countUserJobApply(request.getJobId(), userId) > 0) {
            throw new BusinessException(ResultCode.BAD_REQUEST, "同一职位不能重复投递");
        }
        Long companyId = applyMapper.selectJobCompanyId(request.getJobId());
        if (companyId == null) throw new BusinessException(ResultCode.NOT_FOUND, "职位不存在或已下架");
        JobApply apply = new JobApply();
        apply.setJobId(request.getJobId()); apply.setUserId(userId); apply.setResumeId(request.getResumeId());
        apply.setCompanyId(companyId); apply.setStatus(0); apply.setApplyTime(LocalDateTime.now()); apply.setDeleted(0);
        if (applyMapper.insert(apply) != 1) throw new BusinessException(ResultCode.INTERNAL_ERROR, "投递失败");
        return applyMapper.selectRecordById(apply.getId());
    }

    @Override
    public Page<ApplyRecordVO> getMyApplies(Long userId, long pageNum, long pageSize) {
        return applyMapper.selectMyPage(new Page<>(safePage(pageNum), safeSize(pageSize)), userId);
    }

    @Override
    public Page<ApplyRecordVO> getJobApplies(Long companyUserId, Long jobId, long pageNum, long pageSize) {
        if (!companyUserId.equals(applyMapper.selectJobCompanyUserId(jobId))) {
            throw new BusinessException(ResultCode.FORBIDDEN, "无权查看该职位的投递记录");
        }
        return applyMapper.selectJobPage(new Page<>(safePage(pageNum), safeSize(pageSize)), jobId, companyUserId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateStatus(Long companyUserId, Long id, ApplyStatusDTO request) {
        JobApply apply = applyMapper.selectById(id);
        if (apply == null) throw new BusinessException(ResultCode.NOT_FOUND, "投递记录不存在");
        if (!companyUserId.equals(applyMapper.selectJobCompanyUserId(apply.getJobId()))) {
            throw new BusinessException(ResultCode.FORBIDDEN, "无权处理该投递");
        }
        apply.setStatus(request.getStatus()); apply.setRemark(request.getRemark());
        applyMapper.updateById(apply);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void withdraw(Long userId, Long id) {
        JobApply apply = applyMapper.selectById(id);
        if (apply == null || !userId.equals(apply.getUserId())) throw new BusinessException(ResultCode.NOT_FOUND, "投递记录不存在");
        if (!Integer.valueOf(0).equals(apply.getStatus())) throw new BusinessException(ResultCode.BAD_REQUEST, "只有待处理的投递可以撤回");
        applyMapper.deleteById(id);
    }

    private long safePage(long value) { return value < 1 ? 1 : value; }
    private long safeSize(long value) { return value < 1 || value > 100 ? 10 : value; }
}
