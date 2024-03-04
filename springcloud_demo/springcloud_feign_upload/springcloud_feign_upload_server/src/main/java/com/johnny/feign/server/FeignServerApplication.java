package com.johnny.feign.server;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 服务提供方（接收文件）
 *
 * @author Johnny
 * @since 2019/6/1-17:35
 */
@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class FeignServerApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(FeignServerApplication.class).run(args);
    }
}
