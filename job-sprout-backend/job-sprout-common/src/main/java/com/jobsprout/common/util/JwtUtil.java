package com.jobsprout.common.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import java.util.Date;

public final class JwtUtil {
    private JwtUtil() {}
    private static final String DEFAULT_SECRET = "JobSproutJwtSecretKeyForDevelopmentOnly2026";
    private static final long EXPIRE_MILLIS = 7 * 24 * 60 * 60 * 1000L;
    private static SecretKey key(String secret) { return Keys.hmacShaKeyFor(secret.getBytes(java.nio.charset.StandardCharsets.UTF_8)); }
    public static String createToken(Long userId, String role) { return createToken(userId, role, DEFAULT_SECRET); }
    public static String createToken(Long userId, String role, String secret) {
        Date now = new Date();
        return Jwts.builder().subject(String.valueOf(userId)).claim("role", role).issuedAt(now).expiration(new Date(now.getTime()+EXPIRE_MILLIS)).signWith(key(secret)).compact();
    }
    public static Claims parse(String token) { return parse(token, DEFAULT_SECRET); }
    public static Claims parse(String token, String secret) { return Jwts.parser().verifyWith(key(secret)).build().parseSignedClaims(token).getPayload(); }
    public static Long getUserId(String token) { return Long.valueOf(parse(token).getSubject()); }
}
