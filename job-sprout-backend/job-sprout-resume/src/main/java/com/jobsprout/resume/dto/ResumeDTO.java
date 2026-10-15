package com.jobsprout.resume.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

/** 创建或更新简历的请求参数。 */
@Data
public class ResumeDTO {
    @NotBlank(message = "真实姓名不能为空")
    @Size(max = 50, message = "真实姓名不能超过 50 个字符")
    private String realName;
    private Integer gender;
    private LocalDate birthday;
    @Pattern(regexp = "^$|^1[3-9]\\d{9}$", message = "手机号格式不正确")
    private String phone;
    @Email(message = "邮箱格式不正确")
    private String email;
    @Size(max = 50, message = "学历不能超过 50 个字符")
    private String education;
    private String school;
    private String major;
    private String graduationYear;
    private String skills;
    private String selfIntro;
    private String projectExperience;
    private String internshipExperience;
    private String avatar;
}
