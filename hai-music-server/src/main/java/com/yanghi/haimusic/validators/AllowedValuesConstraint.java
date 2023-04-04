package com.yanghi.haimusic.validators;

import com.yanghi.haimusic.validators.validators.AllowedValuesValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AllowedValuesValidator.class)
public @interface AllowedValuesConstraint {

    String message() default "值必须为[1, 2, 3]其中一个，且不为 null";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
