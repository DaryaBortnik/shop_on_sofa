package com.churilovich.bortnik.darya.shop.on.sofa.service.exception;

public class ReviewServiceRuntimeException extends RuntimeException {
    public ReviewServiceRuntimeException(String message) {
        super(message);
    }

    public ReviewServiceRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
