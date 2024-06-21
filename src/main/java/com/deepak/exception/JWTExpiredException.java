package com.deepak.exception;

public class JWTExpiredException extends RuntimeException {
    public JWTExpiredException(String message) {
        super(message);
    }
}

