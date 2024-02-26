package com.johnny.rabbitmq.service.impl;

import com.johnny.rabbitmq.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
public class EmailServiceImpl implements EmailService {
    @Resource(name = "rabbitTemplate")
    private RabbitTemplate rabbitTemplate;

    @Value("${mq.exchange}")
    private String exchange;

    @Value("${mq.routekey}")
    private String routeKey;

    @Override
    public void sendEmail(String message) {
        try {
            rabbitTemplate.convertAndSend(exchange, routeKey, message);
        } catch (Exception e) {
            log.error("EmailServiceImpl.sendEmail:{}", ExceptionUtils.getMessage(e));
        }
    }
}
