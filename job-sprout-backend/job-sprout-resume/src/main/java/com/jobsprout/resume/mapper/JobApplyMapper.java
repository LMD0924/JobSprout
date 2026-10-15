package com.jobsprout.resume.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jobsprout.resume.entity.JobApply;
import com.jobsprout.resume.vo.ApplyRecordVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/** 投递记录数据访问层，同时负责读取职位和企业展示信息。 */
@Mapper
public interface JobApplyMapper extends BaseMapper<JobApply> {
    @Select("SELECT company_id FROM job WHERE id = #{jobId} AND deleted = 0")
    Long selectJobCompanyId(@Param("jobId") Long jobId);

    @Select("SELECT ci.user_id FROM job j JOIN company_info ci ON ci.id = j.company_id AND ci.deleted = 0 "
            + "WHERE j.id = #{jobId} AND j.deleted = 0")
    Long selectJobCompanyUserId(@Param("jobId") Long jobId);

    @Select("SELECT COUNT(1) FROM job_apply WHERE job_id = #{jobId} AND user_id = #{userId} AND deleted = 0")
    long countUserJobApply(@Param("jobId") Long jobId, @Param("userId") Long userId);

    @Select("SELECT ja.id, ja.job_id AS jobId, ja.user_id AS userId, ja.resume_id AS resumeId, ja.company_id AS companyId, "
            + "ja.status, ja.remark, ja.apply_time AS applyTime, ja.update_time AS updateTime, "
            + "j.title AS jobTitle, ci.company_name AS companyName "
            + "FROM job_apply ja LEFT JOIN job j ON j.id = ja.job_id AND j.deleted = 0 "
            + "LEFT JOIN company_info ci ON ci.id = ja.company_id AND ci.deleted = 0 "
            + "WHERE ja.user_id = #{userId} AND ja.deleted = 0 ORDER BY ja.apply_time DESC")
    Page<ApplyRecordVO> selectMyPage(Page<ApplyRecordVO> page, @Param("userId") Long userId);

    @Select("SELECT ja.id, ja.job_id AS jobId, ja.user_id AS userId, ja.resume_id AS resumeId, ja.company_id AS companyId, "
            + "ja.status, ja.remark, ja.apply_time AS applyTime, ja.update_time AS updateTime, "
            + "j.title AS jobTitle, ci.company_name AS companyName "
            + "FROM job_apply ja LEFT JOIN job j ON j.id = ja.job_id AND j.deleted = 0 "
            + "LEFT JOIN company_info ci ON ci.id = ja.company_id AND ci.deleted = 0 "
            + "WHERE ja.job_id = #{jobId} AND ci.user_id = #{companyId} AND ja.deleted = 0 ORDER BY ja.apply_time DESC")
    Page<ApplyRecordVO> selectJobPage(Page<ApplyRecordVO> page, @Param("jobId") Long jobId,
                                       @Param("companyId") Long companyId);

    @Select("SELECT ja.id, ja.job_id AS jobId, ja.user_id AS userId, ja.resume_id AS resumeId, ja.company_id AS companyId, "
            + "ja.status, ja.remark, ja.apply_time AS applyTime, ja.update_time AS updateTime, "
            + "j.title AS jobTitle, ci.company_name AS companyName "
            + "FROM job_apply ja LEFT JOIN job j ON j.id = ja.job_id AND j.deleted = 0 "
            + "LEFT JOIN company_info ci ON ci.id = ja.company_id AND ci.deleted = 0 "
            + "WHERE ja.id = #{id} AND ja.deleted = 0")
    ApplyRecordVO selectRecordById(@Param("id") Long id);
}
