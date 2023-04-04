package com.yanghi.haimusic.validators.validators;

import com.yanghi.haimusic.validators.OnlyOneField;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;

public class OnlyOneFieldValidator implements ConstraintValidator<OnlyOneField, Object> {

    private String[] fields;

    @Override
    public void initialize(OnlyOneField constraintAnnotation) {
        fields = constraintAnnotation.fields();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        int count = 0;
        for (String field : fields) {
            try {
                Field declaredField = value.getClass().getDeclaredField(field);
                declaredField.setAccessible(true);
                Object fieldValue = declaredField.get(value);
                if (fieldValue != null) {
                    count++;
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return count == 1;
    }
}
