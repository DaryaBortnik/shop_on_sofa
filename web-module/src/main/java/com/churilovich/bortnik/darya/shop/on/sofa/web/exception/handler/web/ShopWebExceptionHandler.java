package com.churilovich.bortnik.darya.shop.on.sofa.web.exception.handler.web;

import com.churilovich.bortnik.darya.shop.on.sofa.service.exception.GetByParameterServiceException;
import com.churilovich.bortnik.darya.shop.on.sofa.web.controller.web.sale.SaleShopWebController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.lang.invoke.MethodHandles;

@ControllerAdvice(assignableTypes = {SaleShopWebController.class})
public class ShopWebExceptionHandler {
    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    @ExceptionHandler(GetByParameterServiceException.class)
    public final String getByParameterExceptionHandling(Exception e) {
        logger.error("Error :{}", e.getMessage());
        return "redirect:/user/sale/shop/add";
    }
}
