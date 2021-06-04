package com.churilovich.bortnik.darya.shop.on.sofa.web.exception.handler.web;

import com.churilovich.bortnik.darya.shop.on.sofa.service.exception.AddServiceException;
import com.churilovich.bortnik.darya.shop.on.sofa.service.exception.DeleteByIdServiceException;
import com.churilovich.bortnik.darya.shop.on.sofa.service.exception.GetByParameterServiceException;
import com.churilovich.bortnik.darya.shop.on.sofa.service.exception.GetOnPageServiceException;
import com.churilovich.bortnik.darya.shop.on.sofa.service.exception.ServiceException;
import com.churilovich.bortnik.darya.shop.on.sofa.service.exception.UpdateParameterServiceException;
import com.churilovich.bortnik.darya.shop.on.sofa.service.exception.ValidationServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.xml.bind.ValidationException;
import java.lang.invoke.MethodHandles;

@ControllerAdvice(basePackages = {"com.churilovich.bortnik.darya.shop.on.sofa.web.controller.web"})
public class WebExceptionHandler {
    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    @ExceptionHandler(UpdateParameterServiceException.class)
    public final String updateParametersExceptionHandling(ServiceException e) {
        logger.error("Error :{}", e.getMessage());
        return "redirect:/error/400";
    }

    @ExceptionHandler(GetOnPageServiceException.class)
    public final String getOnPageExceptionHandling(ServiceException e) {
        logger.error("Error :{}", e.getMessage());
        return "redirect:/error/404";
    }

    @ExceptionHandler(GetByParameterServiceException.class)
    public final String getByParameterExceptionHandling(ServiceException e) {
        logger.error("Error :{}", e.getMessage());
        return "redirect:/error/404";
    }

    @ExceptionHandler(DeleteByIdServiceException.class)
    public final String deleteByIdExceptionHandling(ServiceException e) {
        logger.error("Error :{}", e.getMessage());
        return "redirect:/error/404";
    }

    @ExceptionHandler(AddServiceException.class)
    public final String addEntityExceptionHandling(ServiceException e) {
        logger.error("Error :{}", e.getMessage());
        return "redirect:/error/409";
    }

    @ExceptionHandler(ValidationServiceException.class)
    public final String validationExceptionHandling(ServiceException e) {
        logger.error("Error :{}", e.getMessage());
        return "redirect:/user/profile";
    }
}
