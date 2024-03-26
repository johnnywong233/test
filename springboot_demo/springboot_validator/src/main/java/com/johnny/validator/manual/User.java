package com.johnny.validator.manual;

import jakarta.validation.GroupSequence;
import lombok.Data;
import lombok.NoArgsConstructor;

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