package com.johnny.rabbitmq.activator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@ComponentScan(basePackages = {"com.johnny.rabbitmq"})
@PropertySource(value = {"classpath:application.properties"})
public class RunMain {

    public static void main(String[] args) {
        SpringApplication.run(RunMain.class, args);
    }
}