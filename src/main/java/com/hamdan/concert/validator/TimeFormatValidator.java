package com.hamdan.concert.validator;


import com.hamdan.concert.util.CustomUtil;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class TimeFormatValidator implements ConstraintValidator<TimeFormatConstraint, String> {

    @Override
    public boolean isValid(String time, ConstraintValidatorContext cxt) {
        return CustomUtil.validateTimeFormat(time);
    }
}
