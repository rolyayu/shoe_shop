package com.github.shoe_shop.shared.validators;

import com.github.shoe_shop.shared.constraints.UNP;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UNPConstraintValidator implements ConstraintValidator<UNP, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value.matches("^\\d{14}$");
    }
}
