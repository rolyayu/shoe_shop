package com.github.shoe_shop.base.constraints;

import com.github.shoe_shop.base.validators.IPConstraintValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Constraint(validatedBy = IPConstraintValidator.class)
public @interface IP {
    String message() default "Given string in not IP";
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
