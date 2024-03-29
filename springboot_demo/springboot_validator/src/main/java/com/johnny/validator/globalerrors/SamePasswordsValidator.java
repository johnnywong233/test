package com.johnny.validator.globalerrors;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class SamePasswordsValidator implements ConstraintValidator<SamePasswords, PasswordForm> {

    @Override
    public void initialize(SamePasswords constraintAnnotation) {
    }

    @Override
    public boolean isValid(PasswordForm value, ConstraintValidatorContext context) {
        return value.getConfirmedPassword() == null || value.getConfirmedPassword().equals(value.getPassword());
    }
}
