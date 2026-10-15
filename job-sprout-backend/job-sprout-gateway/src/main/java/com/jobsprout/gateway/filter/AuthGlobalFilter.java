package com.jobsprout.gateway.filter;

import com.jobsprout.common.config.JwtProperties;
import com.jobsprout.common.util.JwtUtil;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Set;

/**
 * 网关 JWT 鉴权过滤器：白名单直接放行，其他请求校验 token 后把用户信息传给下游。
 */
@Component
public class AuthGlobalFilter implements GlobalFilter, Ordered {
    private static final Set<String> WHITE_LIST = Set.of(
            "/api/auth/login", "/api/auth/register", "/doc.html"
    );
    private final JwtUtil jwtUtil;
    private final JwtProperties jwtProperties;

    public AuthGlobalFilter(JwtUtil jwtUtil, JwtProperties jwtProperties) {
        this.jwtUtil = jwtUtil;
        this.jwtProperties = jwtProperties;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();
        if (isWhiteListed(path) || "OPTIONS".equalsIgnoreCase(exchange.getRequest().getMethod().name())) {
            return chain.filter(exchange);
        }
        String authorization = exchange.getRequest().getHeaders().getFirst(jwtProperties.getHeader());
        String prefix = jwtProperties.getPrefix() == null ? "Bearer " : jwtProperties.getPrefix();
        String token = authorization == null ? "" : authorization.startsWith(prefix)
                ? authorization.substring(prefix.length()).trim() : authorization.trim();
        if (!jwtUtil.validateToken(token)) return unauthorized(exchange);
        try {
            ServerWebExchange forwarded = exchange.mutate().request(request -> request.headers(headers -> {
                headers.remove("X-User-Id");
                headers.remove("X-Username");
                headers.remove("X-Role");
                headers.add("X-User-Id", String.valueOf(jwtUtil.getUserId(token)));
                headers.add("X-Username", jwtUtil.getUsername(token));
                headers.add("X-Role", jwtUtil.getRole(token));
            })).build();
            return chain.filter(forwarded);
        } catch (RuntimeException ex) {
            return unauthorized(exchange);
        }
    }

    private boolean isWhiteListed(String path) {
        return WHITE_LIST.contains(path) || path.startsWith("/webjars/")
                || "/v3/api-docs".equals(path) || path.startsWith("/v3/api-docs/");
    }

    private Mono<Void> unauthorized(ServerWebExchange exchange) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
        byte[] body = "{\"code\":401,\"message\":\"未登录或登录已过期\",\"data\":null}".getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(body);
        return exchange.getResponse().writeWith(Mono.just(buffer));
    }

    @Override
    public int getOrder() {
        return -100;
    }
}
