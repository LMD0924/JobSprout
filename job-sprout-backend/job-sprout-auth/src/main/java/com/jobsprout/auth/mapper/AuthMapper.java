package com.jobsprout.auth.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper; import com.jobsprout.auth.entity.AuthEntity; import org.apache.ibatis.annotations.Mapper;
@Mapper public interface AuthMapper extends BaseMapper<AuthEntity> {}