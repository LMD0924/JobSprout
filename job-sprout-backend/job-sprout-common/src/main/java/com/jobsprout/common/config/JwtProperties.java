package com.jobsprout.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/** 从 application.yml 的 jwt 节点读取配置，expire 单位为毫秒。 */
@Data
@Component
@ConfigurationProperties(prefix="jwt")
public class JwtProperties {
    private String secret="JobSproutJwtSecretKeyChangeInProduction2026";
    private Long expire=604800000L;
    private String header="Authorization";
    private String prefix="Bearer ";
}
