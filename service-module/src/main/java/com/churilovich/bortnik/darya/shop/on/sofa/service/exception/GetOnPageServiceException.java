package com.churilovich.bortnik.darya.shop.on.sofa.service.exception;

public class GetOnPageServiceException extends RuntimeException {
    public GetOnPageServiceException(String message) {
        super(message);
    }

    public GetOnPageServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
