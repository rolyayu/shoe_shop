package com.github.shoe_shop.base.validators;

import com.github.shoe_shop.base.constraints.IP;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.util.StringUtils;

import java.util.Arrays;

public class IPConstraintValidator implements ConstraintValidator<IP,String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return !StringUtils.hasLength(value) || isIPv4(value) || isIPv6(value);
    }

    private boolean isIPv4(String address) {
        return Arrays.stream(address.split("\\.")).map(String::trim)
                .filter(subAddress -> !subAddress.startsWith("0"))
                .map(Integer::parseInt)
                .filter(subAddress -> subAddress >= 0 && subAddress <= 255)
                .count() == 4;
    }

    private boolean isIPv6(String address) {
        return address.matches("^([a-fA-F0-9]{1,4}:){7}[a-fA-F0-9]{1,4}$");
    }
}
