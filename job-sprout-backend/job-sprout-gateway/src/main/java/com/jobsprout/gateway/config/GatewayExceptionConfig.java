package com.jobsprout.gateway.config;

import com.jobsprout.gateway.exception.GatewayExceptionHandler;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.result.view.ViewResolver;

import java.util.List;

/** 网关异常处理配置。 */
@Configuration
public class GatewayExceptionConfig {

    @Bean
    public GatewayExceptionHandler gatewayExceptionHandler(
            ServerCodecConfigurer codecConfigurer,
            List<ViewResolver> viewResolvers,
            WebProperties webProperties) {
        return new GatewayExceptionHandler(
                codecConfigurer.getReaders(),
                codecConfigurer.getWriters(),
                viewResolvers
        );
    }
}
