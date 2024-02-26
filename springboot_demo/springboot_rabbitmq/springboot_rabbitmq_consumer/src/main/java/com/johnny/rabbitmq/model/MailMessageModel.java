package com.johnny.rabbitmq.model;

import lombok.Data;

@Data
public class MailMessageModel {
    private String from;
    private String to;
    private String subject;
    private String text;
}