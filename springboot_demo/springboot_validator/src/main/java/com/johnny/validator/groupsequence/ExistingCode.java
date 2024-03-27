package com.johnny.validator.groupsequence;

import jakarta.annotation.Resource;
import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import org.springframework.stereotype.Service;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE}) // class level constraint
@Retention(RUNTIME)
@Constraint(validatedBy = ExistingCodeValidator.class) // validator
@Documented
public @interface ExistingCode {
    String message() default "invalid code"; // default error message

    java.lang.Class<?>[] groups() default {}; // required

    java.lang.Class<? extends Payload>[] payload() default {}; // required
}

@Service
class ExistingCodeValidator implements ConstraintValidator<ExistingCode, Code> {

    @Resource
    private TokenRetrievalService tokenRetrievalService;

    @Override
    public void initialize(ExistingCode constraintAnnotation) {

    }

    @Override
    public boolean isValid(Code value, ConstraintValidatorContext context) {
        try {
            value.withToken(tokenRetrievalService.getToken(value.getCode()));
        } catch (TokenNotFoundException e) {
            if (context != null) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("Code is invalid!")
                        .addPropertyNode("code") // to be tested otherwise refactoring may break the validation
                        .addConstraintViolation();
            }
            return false;
        }
        return true;
    }
}