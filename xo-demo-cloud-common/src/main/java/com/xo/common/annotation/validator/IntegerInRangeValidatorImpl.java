package com.xo.common.annotation.validator;



import com.xo.common.annotation.IntegerInRange;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IntegerInRangeValidatorImpl implements ConstraintValidator<IntegerInRange, Integer> {

    // 符合条件的数
    private int[] values;

    @Override
    public void initialize(IntegerInRange constraintAnnotation) {
        this.values = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if (values == null || values.length == 0) {
            return true;
        }
        if (value == null) {
            return true;
        }
        for (int i : values) {
            if (value == i) {
                return true;
            }
        }
        return false;
    }
}