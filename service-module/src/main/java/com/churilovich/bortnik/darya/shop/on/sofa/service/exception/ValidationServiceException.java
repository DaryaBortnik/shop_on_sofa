package com.churilovich.bortnik.darya.shop.on.sofa.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ValidationServiceException extends RuntimeException {
    public ValidationServiceException(String message) {
        super(message);
    }
}
