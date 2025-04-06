package com.github.shoe_shop.base.validators;

import com.github.shoe_shop.base.constraints.UNP;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UNPConstraintValidator implements ConstraintValidator<UNP, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value.matches("^\\d{14}$");
    }
}
