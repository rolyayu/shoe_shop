package com.github.shoe_shop.shared;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ValidatorUtils {

    private static final String RESPONSE_ON_EMPTY_MESSAGE = "No validation constraint message given. Try to specify message in constraint annotation";

    private final Validator validator;

    public  <T> String getValidationError(Class<T> objectClass, T object) {
        final Set<ConstraintViolation<T>> constraintViolations = validator.validate(object);
        if (constraintViolations.isEmpty()) {
            return null;
        }
        final String validationsMessagesConcat = constraintViolations.stream().map(ConstraintViolation::getMessage)
                .collect(Collectors.joining("\n"));
        return StringUtils.hasLength(validationsMessagesConcat.trim()) ? validationsMessagesConcat : RESPONSE_ON_EMPTY_MESSAGE;
    }
}
