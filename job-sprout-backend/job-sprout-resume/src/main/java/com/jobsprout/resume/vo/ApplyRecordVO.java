package com.jobsprout.resume.vo;

import lombok.Data;

import java.time.LocalDateTime;

/** 投递记录及职位、企业展示信息。 */
@Data
public class ApplyRecordVO {
    private Long id;
    private Long jobId;
    private Long userId;
    private Long resumeId;
    private Long companyId;
    private Integer status;
    private String remark;
    private LocalDateTime applyTime;
    private LocalDateTime updateTime;
    private String jobTitle;
    private String companyName;
}
