package com.johnny.validator.validationgroups;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Size;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Size(min = 4, max = 10) // constraint composition
// @Pattern(regexp = ".*(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).*$")
@Target({FIELD, METHOD, ANNOTATION_TYPE}) // 
@Retention(RUNTIME)
@Constraint(validatedBy = {}) // composite constraint with no validator
@Documented
public @interface StrongPassword {
    String message() default ""; // required

    Class<?>[] groups() default {}; // required

    Class<? extends Payload>[] payload() default {}; // required
}