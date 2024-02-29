package com.johnny.validator.custom;

import org.apache.commons.beanutils.BeanUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class OneOfValidator implements ConstraintValidator<OneOf, Object> {

    private String[] fields;

    @Override
    public void initialize(OneOf constraintAnnotation) {
        fields = constraintAnnotation.fields();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (fields == null || fields.length == 0) {
            return true;
        }
        try {
            int notNullFieldCount = 0;
            for (String field : fields) {
                String fieldObj = BeanUtils.getProperty(value, field);
                if (fieldObj != null) {
                    if (!isNullOrEmpty(fieldObj)) {
                        notNullFieldCount++;
                    }
                }
            }
            return notNullFieldCount == 1;
        } catch (Exception e) {
            // ignore
        }
        return false;
    }

    private boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }

}
