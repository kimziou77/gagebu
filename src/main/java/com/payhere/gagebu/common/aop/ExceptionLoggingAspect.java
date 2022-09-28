package com.payhere.gagebu.common.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
class ExceptionLoggingAspect {

    private static final String EXCEPTION_LOG_MESSAGE = "[EXCEPTION] {} : {}";

    @Pointcut("execution(* com.payhere.gagebu.common.exception.handler.*Handler.*(..))")
    public void loggerPointCut() {
    }

    @Around("loggerPointCut()")
    public Object methodLogger(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        var result = proceedingJoinPoint.proceed();
        var arg = proceedingJoinPoint.getArgs()[0];

        if (arg instanceof Exception e) {
            log.warn(EXCEPTION_LOG_MESSAGE, e.getClass().getSimpleName(), e.getMessage());
        }

        return result;
    }
}
