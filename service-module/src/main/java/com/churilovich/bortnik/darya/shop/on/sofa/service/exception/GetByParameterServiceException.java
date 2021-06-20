package com.churilovich.bortnik.darya.shop.on.sofa.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class GetByParameterServiceException extends ServiceException {
    public GetByParameterServiceException(String message) {
        super(message);
    }

    public GetByParameterServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
