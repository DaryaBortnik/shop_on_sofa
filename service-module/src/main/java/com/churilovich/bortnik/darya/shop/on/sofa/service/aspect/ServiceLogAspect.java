package com.churilovich.bortnik.darya.shop.on.sofa.service.aspect;

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
public class ServiceLogAspect {
    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    @Pointcut("execution(public * com.churilovich.bortnik.darya.shop.on.sofa.service.*.*(..))")
    public void callAtService() {
    }

    @Before("callAtService()")
    public void beforeCallAtService(JoinPoint joinPoint) {
        logger.info("Start operation [ " + joinPoint.getSignature().getName() + " ] on service level");
    }
}
