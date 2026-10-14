package com.jobsprout.auth;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/** Auth 服务启动入口。 */
@SpringBootApplication(scanBasePackages = "com.jobsprout")
@EnableDiscoveryClient
@EnableFeignClients
@MapperScan("com.jobsprout.auth.mapper")
public class AuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
    }
}
