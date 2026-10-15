package com.jobsprout.resume.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/** job_apply 投递记录实体。 */
@Data
@TableName("job_apply")
public class JobApply {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private Long jobId;
    private Long userId;
    private Long resumeId;
    private Long companyId;
    private Integer status;
    private String remark;
    private LocalDateTime applyTime;
    private LocalDateTime updateTime;
    @TableLogic
    private Integer deleted;
}
