package com.johnny.validator.manual;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.GroupSequence;

@GroupSequence(value = {ValidationOrder.First.class, ValidationOrder.Second.class})
interface ValidationOrder {
    interface First {
    }

    interface Second {
    }
}

@NoArgsConstructor
@Data
public class User {
    @UserExists(groups = ValidationOrder.First.class)
    @UserIsEntitledToDiscount(groups = ValidationOrder.Second.class)
    private String username;
}