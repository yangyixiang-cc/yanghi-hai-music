package com.yanghi.haimusic.validators;

import com.yanghi.haimusic.validators.validators.AllowedValuesValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AllowedValuesValidator.class)
public @interface AllowedValuesConstraint {

    String message() default "值必须为[1, 2, 3]其中一个";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
