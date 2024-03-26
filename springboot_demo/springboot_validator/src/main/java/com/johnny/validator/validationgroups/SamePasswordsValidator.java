package com.johnny.validator.validationgroups;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class SamePasswordsValidator implements ConstraintValidator<SamePasswords, PasswordAware> {

    @Override
    public void initialize(SamePasswords constraintAnnotation) {
    }

    @Override
    public boolean isValid(PasswordAware value, ConstraintValidatorContext context) {
        return value.getConfirmedPassword() == null || value.getConfirmedPassword().equals(value.getPassword());
    }
}