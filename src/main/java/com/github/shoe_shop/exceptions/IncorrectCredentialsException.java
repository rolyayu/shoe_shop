package com.github.shoe_shop.exceptions;

import org.springframework.security.authentication.BadCredentialsException;

public class IncorrectCredentialsException extends BadCredentialsException {
    public IncorrectCredentialsException(String msg) {
        super(msg);
    }

    public IncorrectCredentialsException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
