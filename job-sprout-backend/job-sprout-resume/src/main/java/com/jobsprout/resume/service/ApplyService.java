package com.jobsprout.resume.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jobsprout.resume.dto.ApplyDTO;
import com.jobsprout.resume.dto.ApplyStatusDTO;
import com.jobsprout.resume.vo.ApplyRecordVO;

public interface ApplyService {
    ApplyRecordVO apply(Long userId, ApplyDTO request);
    Page<ApplyRecordVO> getMyApplies(Long userId, long pageNum, long pageSize);
    Page<ApplyRecordVO> getJobApplies(Long companyUserId, Long jobId, long pageNum, long pageSize);
    void updateStatus(Long companyUserId, Long id, ApplyStatusDTO request);
    void withdraw(Long userId, Long id);
}
