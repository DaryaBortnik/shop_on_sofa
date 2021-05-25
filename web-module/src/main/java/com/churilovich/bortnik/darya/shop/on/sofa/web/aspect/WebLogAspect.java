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
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

    @Before("callAtApiController()")
    public void beforeCallAtApiController(JoinPoint joinPoint) {
        List<String> args = Arrays.stream(joinPoint.getArgs())
                .map(Object::toString)
                .collect(Collectors.toList());
        logger.info("Start operation [ " + joinPoint.getSignature().getName() + " ] on api controller level" +
                " with parameters: " + args);
    }

    @Before("callAtAdminWebController()")
    public void beforeCallAtAdminWebController(JoinPoint joinPoint) {
        List<String> args = Arrays.stream(joinPoint.getArgs())
                .map(Object::toString)
                .collect(Collectors.toList());
        logger.info("Start operation [ " + joinPoint.getSignature().getName() + " ] on web controller level" +
                " with parameters: " + args);
    }

    @Before("callAtCustomerWebController()")
    public void beforeCallAtCustomerWebController(JoinPoint joinPoint) {
        List<String> args = Arrays.stream(joinPoint.getArgs())
                .map(Object::toString)
                .collect(Collectors.toList());
        logger.info("Start operation [ " + joinPoint.getSignature().getName() + " ] on web controller level" +
                " with parameters: " + args);
    }

    @Before("callAtSaleWebController()")
    public void beforeCallAtSaleWebController(JoinPoint joinPoint) {
        List<String> args = Arrays.stream(joinPoint.getArgs())
                .filter(Objects::nonNull)
                .map(Objects::toString)
                .collect(Collectors.toList());
        logger.info("Start operation [ " + joinPoint.getSignature().getName() + " ] on web controller level" +
                " with parameters: " + args);
    }
}
