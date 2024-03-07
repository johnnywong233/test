package com.johnny.rabbitmq.service;

public interface EmailService {

    /**
     * 发送邮件任务存入消息队列
     */
    void sendEmail(String message);
}