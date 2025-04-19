package com.github.shoe_shop.exceptions;

import org.springframework.security.core.AuthenticationException;

public class IncorrectCredentialsException extends AuthenticationException {
    public IncorrectCredentialsException(String msg) {
        super(msg);
    }

    public IncorrectCredentialsException() {
        super("Password or username is incorrect");
    }

    public IncorrectCredentialsException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
