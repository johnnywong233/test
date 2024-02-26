package com.johnny.rabbitmq.model;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

@Data
public class MailMessageModel {
    @JSONField(name = "from")
    private String from;

    @JSONField(name = "to")
    private String to;

    @JSONField(name = "subject")
    private String subject;

    @JSONField(name = "text")
    private String text;

    @Override
    public String toString() {
        return "Email{from:" + this.from + ", " +
                "to:" + this.to + ", " +
                "subject:" + this.subject + ", " +
                "text:" + this.text + "}";
    }
}