package com.jobsprout.resume.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jobsprout.resume.entity.Resume;
import org.apache.ibatis.annotations.Mapper;

/** 简历数据访问层。 */
@Mapper
public interface ResumeMapper extends BaseMapper<Resume> {
}
