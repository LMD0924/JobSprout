package com.jobsprout.auth.controller;

import com.jobsprout.auth.dto.LoginDTO;
import com.jobsprout.auth.dto.RegisterDTO;
import com.jobsprout.auth.service.AuthService;
import com.jobsprout.auth.vo.LoginVO;
import com.jobsprout.auth.vo.UserInfoVO;
import com.jobsprout.common.result.Result;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** 登录、注册和当前用户信息接口。 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private static final String BEARER_PREFIX = "Bearer ";
    private final AuthService authService;

    @PostMapping("/register")
    public Result<Void> register(@Valid @RequestBody RegisterDTO request) {
        authService.register(request);
        return Result.success();
    }

    @PostMapping("/login")
    public Result<LoginVO> login(@Valid @RequestBody LoginDTO request) {
        return Result.success(authService.login(request));
    }

    @GetMapping("/info")
    public Result<UserInfoVO> info(@RequestHeader(value = "Authorization", required = false) String authorization) {
        String token = authorization == null ? "" : authorization.startsWith(BEARER_PREFIX)
                ? authorization.substring(BEARER_PREFIX.length()) : authorization;
        return Result.success(authService.getUserInfo(token));
    }
}
