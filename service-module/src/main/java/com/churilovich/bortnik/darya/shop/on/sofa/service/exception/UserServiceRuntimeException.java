package com.churilovich.bortnik.darya.shop.on.sofa.service.exception;

public class UserServiceRuntimeException extends RuntimeException {
    public UserServiceRuntimeException(String message) {
        super(message);
    }

    public UserServiceRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
