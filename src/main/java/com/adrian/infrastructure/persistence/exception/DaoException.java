package com.adrian.infrastructure.persistence.exception;

public class DaoException extends RuntimeException {
    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }
}
