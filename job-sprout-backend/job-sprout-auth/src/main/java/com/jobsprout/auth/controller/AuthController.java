package com.jobsprout.auth.controller;

import com.jobsprout.common.result.Result;
import com.jobsprout.common.util.JwtUtil;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @PostMapping("/login")
    public Result<Map<String, Object>> login(@RequestBody Map<String, String> body) {
        String username = body.getOrDefault("username", "");
        String role = username.startsWith("admin") ? "ADMIN" : "STUDENT";
        return Result.success(Map.of("token", JwtUtil.createToken(1L, role), "username", username, "role", role));
    }
    @PostMapping("/register")
    public Result<Void> register(@RequestBody Map<String, String> body) { return Result.success(); }
}
