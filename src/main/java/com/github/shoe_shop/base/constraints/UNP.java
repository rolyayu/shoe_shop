package com.github.shoe_shop.base.constraints;

import com.github.shoe_shop.base.validators.UNPConstraintValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UNPConstraintValidator.class)
public @interface UNP {
    String message() default "Given string in not UNP";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
