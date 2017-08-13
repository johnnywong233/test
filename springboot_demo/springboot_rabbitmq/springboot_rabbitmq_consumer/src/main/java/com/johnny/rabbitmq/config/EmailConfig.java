package com.johnny.rabbitmq.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
@PropertySource(value = {"classpath:mail.properties"})
@ComponentScan(basePackages = {"com.johnny.rabbitmq"})
public class EmailConfig {
    @Autowired
    private Environment env;

    @Bean(name = "mailSender")
    public JavaMailSender mailSender() {
        // 创建邮件发送器, 主要提供了邮件发送接口、透明创建Java Mail的MimeMessage、及邮件发送的配置
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        // 如果为普通邮箱, 非ssl认证等
        mailSender.setHost(env.getProperty("mail.host").trim());
        mailSender.setPort(Integer.parseInt(env.getProperty("mail.port").trim()));
        mailSender.setUsername(env.getProperty("mail.username").trim());
        mailSender.setPassword(env.getProperty("mail.password").trim());
        mailSender.setDefaultEncoding("utf-8");
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.timeout", "25000");
        mailSender.setJavaMailProperties(props);
        return mailSender;
    }
}
