package com.johnny.validator.groupsequence;

import jakarta.validation.GroupSequence;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

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