package com.churilovich.bortnik.darya.shop.on.sofa.web.exception.handler.api;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;
import java.util.Objects;

@ControllerAdvice
public class ApiExceptionHandler extends DefaultErrorAttributes {

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
}
