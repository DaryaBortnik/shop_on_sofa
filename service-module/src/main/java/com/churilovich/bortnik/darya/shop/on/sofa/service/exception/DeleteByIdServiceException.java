package com.churilovich.bortnik.darya.shop.on.sofa.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class DeleteByIdServiceException extends ServiceException {
    public DeleteByIdServiceException(String message) {
        super(message);
    }
}
