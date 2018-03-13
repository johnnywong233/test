package com.johnny.validator.groupsequence;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.GroupSequence;
import javax.validation.constraints.Size;

@GroupSequence(value = {ValidationOrder.First.class, ValidationOrder.Next.class})
interface ValidationOrder {
    interface First {
    }

    interface Next {
    }
}

@Data
@NoArgsConstructor
@ExistingCode(groups = ValidationOrder.Next.class)
public class Code {

    @Size(min = 1, max = 3, groups = ValidationOrder.First.class)
    private String code;

    private transient String token;

    void withToken(String token) {
        this.token = token;
    }
}