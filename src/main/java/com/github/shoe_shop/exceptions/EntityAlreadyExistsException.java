package com.github.shoe_shop.exceptions;

import jakarta.persistence.PersistenceException;

public class EntityAlreadyExistsException extends PersistenceException {
    public EntityAlreadyExistsException(String message) {
        super(message);
    }

    public EntityAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntityAlreadyExistsException(Throwable cause) {
        super(cause);
    }
}
