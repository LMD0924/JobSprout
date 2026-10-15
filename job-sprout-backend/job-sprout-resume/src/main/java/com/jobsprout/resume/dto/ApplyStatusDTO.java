package com.jobsprout.resume.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/** 企业更新投递处理状态请求。 */
@Data
public class ApplyStatusDTO {
    @NotNull(message = "投递状态不能为空")
    @Min(value = 0, message = "投递状态不正确")
    @Max(value = 3, message = "投递状态不正确")
    private Integer status;
    @Size(max = 500, message = "备注不能超过 500 个字符")
    private String remark;
}
