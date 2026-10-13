package com.jobsprout.message.entity;
import com.baomidou.mybatisplus.annotation.*; import lombok.Data; import java.time.LocalDateTime;
@Data @TableName("message") public class MessageEntity { @TableId(type=IdType.AUTO) private Long id; private String name; private LocalDateTime createTime; private LocalDateTime updateTime; @TableLogic private Integer deleted; }