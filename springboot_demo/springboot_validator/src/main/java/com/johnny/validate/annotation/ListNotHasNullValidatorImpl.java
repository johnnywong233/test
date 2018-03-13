package com.johnny.validate.annotation;

import org.springframework.stereotype.Service;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

/**
 * 自定义注解ListNotHasNull 的实现类
 * 用于判断List集合中是否含有null元素
 */
@Service
public class ListNotHasNullValidatorImpl implements ConstraintValidator<ListNotHasNull, List> {

    private int value;

    @Override
    public void initialize(ListNotHasNull constraintAnnotation) {
        //传入value 值，可以在校验中使用
        this.value = constraintAnnotation.value();
    }

    public boolean isValid(List list, ConstraintValidatorContext constraintValidatorContext) {
        for (Object object : list) {
            if (object == null) {
                //如果List集合中含有Null元素，校验失败
                return false;
            }
        }
        return true;
    }
}