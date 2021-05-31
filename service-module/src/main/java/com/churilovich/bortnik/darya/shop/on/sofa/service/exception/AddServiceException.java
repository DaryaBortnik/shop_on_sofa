package com.churilovich.bortnik.darya.shop.on.sofa.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class AddServiceException extends ServiceException {
    public AddServiceException(String message) {
        super(message);
    }

    public AddServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
