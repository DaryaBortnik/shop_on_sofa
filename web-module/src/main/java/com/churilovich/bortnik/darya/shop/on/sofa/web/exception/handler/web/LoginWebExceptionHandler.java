package com.churilovich.bortnik.darya.shop.on.sofa.web.exception.handler.web;

import com.churilovich.bortnik.darya.shop.on.sofa.service.exception.GetByParameterServiceException;
import com.churilovich.bortnik.darya.shop.on.sofa.web.controller.web.common.LoginWebController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

import java.lang.invoke.MethodHandles;

@ControllerAdvice(assignableTypes = LoginWebController.class)
public class LoginWebExceptionHandler extends ExceptionHandlerExceptionResolver {
    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    @ExceptionHandler({GetByParameterServiceException.class, UsernameNotFoundException.class})
    public final String notFoundUserExceptionHandling(UsernameNotFoundException e) {
        logger.error("Error:= {} ", e.getMessage());
        return "error_page";
    }
}
