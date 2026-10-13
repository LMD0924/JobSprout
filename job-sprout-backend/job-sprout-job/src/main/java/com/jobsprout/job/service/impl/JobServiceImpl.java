package com.jobsprout.job.service.impl;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl; import com.jobsprout.job.entity.JobEntity; import com.jobsprout.job.mapper.JobMapper; import com.jobsprout.job.service.JobService; import org.springframework.stereotype.Service;
@Service public class JobServiceImpl extends ServiceImpl<JobMapper, JobEntity> implements JobService {}