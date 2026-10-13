package com.jobsprout.job.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper; import com.jobsprout.job.entity.JobEntity; import org.apache.ibatis.annotations.Mapper;
@Mapper public interface JobMapper extends BaseMapper<JobEntity> {}