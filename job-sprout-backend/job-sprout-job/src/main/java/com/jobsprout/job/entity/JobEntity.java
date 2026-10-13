package com.jobsprout.job.entity;
import com.baomidou.mybatisplus.annotation.*; import lombok.Data; import java.time.LocalDateTime;
@Data @TableName("job") public class JobEntity { @TableId(type=IdType.AUTO) private Long id; private String name; private LocalDateTime createTime; private LocalDateTime updateTime; @TableLogic private Integer deleted; }