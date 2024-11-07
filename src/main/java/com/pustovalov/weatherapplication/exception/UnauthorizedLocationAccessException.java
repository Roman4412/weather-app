package com.pustovalov.weatherapplication.exception;

public class UnauthorizedLocationAccessException extends RuntimeException {

    public UnauthorizedLocationAccessException() {
    }

    public UnauthorizedLocationAccessException(String message) {
        super(message);
    }

    public UnauthorizedLocationAccessException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnauthorizedLocationAccessException(Throwable cause) {
        super(cause);
    }

    public UnauthorizedLocationAccessException(String message,
                                               Throwable cause,
                                               boolean enableSuppression,
                                               boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
