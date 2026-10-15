package com.jobsprout.resume.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/** 学生投递职位请求。 */
@Data
public class ApplyDTO {
    @NotNull(message = "职位不能为空")
    private Long jobId;
    @NotNull(message = "简历不能为空")
    private Long resumeId;
}
