package com.jobsprout.auth.service;

import com.jobsprout.auth.dto.LoginDTO;
import com.jobsprout.auth.dto.RegisterDTO;
import com.jobsprout.auth.vo.LoginVO;
import com.jobsprout.auth.vo.UserInfoVO;

/** 认证服务接口。 */
public interface AuthService {
    void register(RegisterDTO request);
    LoginVO login(LoginDTO request);
    UserInfoVO getUserInfo(String token);
}
