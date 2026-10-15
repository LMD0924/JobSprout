package com.jobsprout.resume.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/** resume 表实体。 */
@Data
@TableName("resume")
public class Resume {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private Long userId;
    private String realName;
    private Integer gender;
    private LocalDate birthday;
    private String phone;
    private String email;
    private String education;
    private String school;
    private String major;
    private String graduationYear;
    private String skills;
    private String selfIntro;
    private String projectExperience;
    private String internshipExperience;
    private String avatar;
    private Integer isDefault;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    @TableLogic
    private Integer deleted;
}
