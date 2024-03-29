package com.johnny.validate.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 自定义注解ListNotHasNull 的实现类
 * 用于判断List集合中是否含有null元素
 */
@Service
public class ListNotHasNullValidatorImpl implements ConstraintValidator<ListNotHasNull, List<Object>> {

    @Override
    public void initialize(ListNotHasNull constraintAnnotation) {
        //传入value 值，可以在校验中使用
        int value = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(List<Object> list, ConstraintValidatorContext constraintValidatorContext) {
        for (Object object : list) {
            if (object == null) {
                //如果List集合中含有Null元素，校验失败
                return false;
            }
        }
        return true;
    }
}