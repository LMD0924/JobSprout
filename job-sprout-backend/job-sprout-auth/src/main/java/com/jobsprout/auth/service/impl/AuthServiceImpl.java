package com.jobsprout.auth.service.impl;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl; import com.jobsprout.auth.entity.AuthEntity; import com.jobsprout.auth.mapper.AuthMapper; import com.jobsprout.auth.service.AuthService; import org.springframework.stereotype.Service;
@Service public class AuthServiceImpl extends ServiceImpl<AuthMapper, AuthEntity> implements AuthService {}