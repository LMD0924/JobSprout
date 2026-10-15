package com.jobsprout.resume.vo;

import com.jobsprout.resume.entity.Resume;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/** 简历展示对象。 */
@Data
public class ResumeVO {
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

    public static ResumeVO from(Resume source) {
        if (source == null) return null;
        ResumeVO target = new ResumeVO();
        target.id = source.getId(); target.userId = source.getUserId(); target.realName = source.getRealName();
        target.gender = source.getGender(); target.birthday = source.getBirthday(); target.phone = source.getPhone();
        target.email = source.getEmail(); target.education = source.getEducation(); target.school = source.getSchool();
        target.major = source.getMajor(); target.graduationYear = source.getGraduationYear(); target.skills = source.getSkills();
        target.selfIntro = source.getSelfIntro(); target.projectExperience = source.getProjectExperience();
        target.internshipExperience = source.getInternshipExperience(); target.avatar = source.getAvatar();
        target.isDefault = source.getIsDefault(); target.createTime = source.getCreateTime(); target.updateTime = source.getUpdateTime();
        return target;
    }
}
