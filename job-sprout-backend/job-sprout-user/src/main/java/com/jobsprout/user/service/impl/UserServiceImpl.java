package com.jobsprout.user.service.impl;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl; import com.jobsprout.user.entity.UserEntity; import com.jobsprout.user.mapper.UserMapper; import com.jobsprout.user.service.UserService; import org.springframework.stereotype.Service;
@Service public class UserServiceImpl extends ServiceImpl<UserMapper, UserEntity> implements UserService {}