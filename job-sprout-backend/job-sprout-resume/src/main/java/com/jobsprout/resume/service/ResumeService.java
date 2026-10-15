package com.jobsprout.resume.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jobsprout.resume.dto.ResumeDTO;
import com.jobsprout.resume.vo.ResumeVO;

public interface ResumeService {
    ResumeVO getMyResume(Long userId);
    ResumeVO getById(Long userId, Long id);
    ResumeVO create(Long userId, ResumeDTO request);
    ResumeVO update(Long userId, Long id, ResumeDTO request);
    void delete(Long userId, Long id);
}

