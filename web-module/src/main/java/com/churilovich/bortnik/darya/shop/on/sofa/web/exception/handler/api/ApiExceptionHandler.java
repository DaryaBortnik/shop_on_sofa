package com.churilovich.bortnik.darya.shop.on.sofa.web.exception.handler.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import javax.xml.bind.ValidationException;
import java.lang.invoke.MethodHandles;
import java.util.Map;
import java.util.Objects;

@ControllerAdvice
public class ApiExceptionHandler extends DefaultErrorAttributes {
    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {
        Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, options);
        Throwable error = getError(webRequest);
        if (Objects.nonNull(error)) {
            errorAttributes.put("message", error.getMessage());
            errorAttributes.remove("trace");
        }
        return errorAttributes;
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public final void validationExceptionHandling(Exception e) {
        logger.error("Error :{}", e.getMessage());
    }
}
