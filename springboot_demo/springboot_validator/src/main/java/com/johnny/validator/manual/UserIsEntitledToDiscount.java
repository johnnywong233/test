package com.johnny.validator.manual;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = UserExistsValidator.class) // validator
@Documented
public @interface UserIsEntitledToDiscount {
    String message() default "user is not entitled to discount"; // default error message

    Class<?>[] groups() default {}; // required

    Class<? extends Payload>[] payload() default {}; // required
}

class UserIsEntitledToDiscountValidator implements ConstraintValidator<UserIsEntitledToDiscount, String> {
    @Override
    public void initialize(UserIsEntitledToDiscount constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return false;
    }
}