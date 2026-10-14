package com.jobsprout.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/** 从 application.yml 的 jwt 节点读取配置，expire 单位为毫秒。 */
@Data
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {
    /** HMAC 密钥，生产环境请使用随机且长度不少于 32 字符的值。 */
    private String secret = "JobSproutJwtSecretKeyChangeInProduction2026";
    /** Token 有效期，默认 7 天。 */
    private Long expire = 604800000L;
    private String header = "Authorization";
    private String prefix = "Bearer ";
}
