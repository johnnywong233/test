package com.johnny.validator.support;

import lombok.Data;

import java.io.Serializable;

/**
 * A message to be displayed in web context. Depending on the type, different style will be applied.
 */
@Data
public class Message implements Serializable {
    /**
     * Name of the flash attribute.
     */
    public static final String MESSAGE_ATTRIBUTE = "message";
    private final String message;
    private final Type type;
    private final Object[] args;

    public Message(String message, Type type) {
        this.message = message;
        this.type = type;
        this.args = null;
    }

    Message(String message, Type type, Object... args) {
        this.message = message;
        this.type = type;
        this.args = args;
    }

    /**
     * The type of the message to be displayed. The type is used to show message in a different style.
     */
    public enum Type {
        DANGER, WARNING, INFO, SUCCESS
    }
}