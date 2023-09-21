package com.cfexlib.datasource.exception;

public class AWSSecretAccessException extends RuntimeException {

    public AWSSecretAccessException(String message) {
        super(message);
    }

    public AWSSecretAccessException(Throwable cause) {
        super(cause);
    }
}
