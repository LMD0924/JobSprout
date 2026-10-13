package com.jobsprout.resume.entity;
import com.baomidou.mybatisplus.annotation.*; import lombok.Data; import java.time.LocalDateTime;
@Data @TableName("resume") public class ResumeEntity { @TableId(type=IdType.AUTO) private Long id; private String name; private LocalDateTime createTime; private LocalDateTime updateTime; @TableLogic private Integer deleted; }