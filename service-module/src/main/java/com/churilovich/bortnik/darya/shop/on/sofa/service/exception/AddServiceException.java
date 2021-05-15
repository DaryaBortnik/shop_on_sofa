package com.churilovich.bortnik.darya.shop.on.sofa.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AddServiceException extends RuntimeException {
    public AddServiceException(String message) {
        super(message);
    }

    public AddServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
