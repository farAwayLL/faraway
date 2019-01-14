package com.sboot.study.annotation.validate;

import com.sboot.study.annotation.SexAnnotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.HashSet;
import java.util.Set;

/**
 * create by faraway on 2018/12/21
 * description:校验性别的自定义校验器
 */
public class SexValidation implements ConstraintValidator<SexAnnotation,Integer> {

    Set<Integer> sexArr;

    @Override
    public void initialize(SexAnnotation constraintAnnotation) {
        sexArr=new HashSet<Integer>();
        sexArr.add(1);
        sexArr.add(2);
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if (sexArr.contains(value)){
            return true;
        }
        return false;
    }
}
