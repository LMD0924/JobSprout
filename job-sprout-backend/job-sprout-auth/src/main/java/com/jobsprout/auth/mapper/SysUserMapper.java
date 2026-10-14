package com.jobsprout.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jobsprout.auth.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;

/** sys_user 数据访问层。 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {
}
