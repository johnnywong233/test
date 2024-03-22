package com.johnny.rabbitmq.config;

import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Objects;
import java.util.Properties;

@Configuration
@PropertySource(value = {"classpath:mail.properties"})
@ComponentScan(basePackages = {"com.johnny.rabbitmq"})
public class EmailConfig {
    @Resource
    private Environment env;

    @Bean(name = "mailSender")
    public JavaMailSender mailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        // 如果为普通邮箱, 非ssl认证等
        mailSender.setHost(env.getProperty("mail.host"));
        mailSender.setPort(Integer.parseInt(Objects.requireNonNull(env.getProperty("mail.port"))));
        mailSender.setUsername(env.getProperty("mail.username"));
        mailSender.setPassword(env.getProperty("mail.password"));
        mailSender.setDefaultEncoding("utf-8");
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.timeout", "25000");
        mailSender.setJavaMailProperties(props);
        return mailSender;
    }
}
