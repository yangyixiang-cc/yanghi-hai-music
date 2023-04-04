package com.yanghi.haimusic.validators.validators;

import com.yanghi.haimusic.validators.AllowedValuesConstraint;
import com.yanghi.haimusic.validators.constant.CommentTypeEnum;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AllowedValuesValidator implements ConstraintValidator<AllowedValuesConstraint, Integer> {

    @Override
    public void initialize(AllowedValuesConstraint constraintAnnotation) {
        // no initialization needed
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        for (CommentTypeEnum allowedValue : CommentTypeEnum.values()) {
            if (allowedValue.getValue() == value) {
                return true;
            }
        }
        return false;
    }
}
