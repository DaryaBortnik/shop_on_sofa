package com.churilovich.bortnik.darya.shop.on.sofa.web.aspect;

import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;

@Component
@Aspect
@Log4j2
public class WebLogAspect {
    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    @Pointcut("execution(public * com.churilovich.bortnik.darya.shop.on.sofa.web.controller.api.*.*(..))")
    public void callAtApiController() {
    }

    @Pointcut("execution(public * com.churilovich.bortnik.darya.shop.on.sofa.web.controller.web.admin.*.*(..))")
    public void callAtAdminWebController() {
    }

    @Pointcut("execution(public * com.churilovich.bortnik.darya.shop.on.sofa.web.controller.web.customer.*.*(..))")
    public void callAtCustomerWebController() {
    }

    @Pointcut("execution(public * com.churilovich.bortnik.darya.shop.on.sofa.web.controller.web.sale.*.*(..))")
    public void callAtSaleWebController() {
    }

    @Pointcut("execution(public * com.churilovich.bortnik.darya.shop.on.sofa.web.controller.web.common.*.*(..))")
    public void callAtCommonWebController() {
    }


    @Before("callAtApiController()")
    public void beforeCallAtApiController(JoinPoint joinPoint) {
        logger.info("Start operation [ " + joinPoint.getSignature().getName() + " ] on api controller level");
    }

    @Before("callAtAdminWebController()")
    public void beforeCallAtAdminWebController(JoinPoint joinPoint) {
        logger.info("Start operation [ " + joinPoint.getSignature().getName() + " ] on web controller level");
    }

    @Before("callAtCustomerWebController()")
    public void beforeCallAtCustomerWebController(JoinPoint joinPoint) {
        logger.info("Start operation [ " + joinPoint.getSignature().getName() + " ] on web controller level");
    }

    @Before("callAtSaleWebController()")
    public void beforeCallAtSaleWebController(JoinPoint joinPoint) {
        logger.info("Start operation [ " + joinPoint.getSignature().getName() + " ] on web controller level");
    }

    @Before("callAtCommonWebController()")
    public void beforeCallAtCommonWebController(JoinPoint joinPoint) {
        logger.info("Start operation [ " + joinPoint.getSignature().getName() + " ] on web controller level");
    }
}
