package com.johnny.rabbitmq.activator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@EnableAutoConfiguration
@ComponentScan(basePackages = {"com.johnny.rabbitmq"})
public class WebMain {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(WebMain.class, args);
    }
}