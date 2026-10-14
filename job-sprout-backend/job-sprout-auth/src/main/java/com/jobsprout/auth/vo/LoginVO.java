package com.jobsprout.auth.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/** 登录成功响应。 */
@Data
@AllArgsConstructor
public class LoginVO {
    private String token;
    private UserInfoVO userInfo;
}
