package com.github.shoe_shop.shared.validators;

import com.github.shoe_shop.shared.constraints.CardNo;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CardNoConstrainValidator implements ConstraintValidator<CardNo, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value.matches("\\d{13,27}");
    }
}
