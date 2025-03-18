package com.shopping.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class LoggingAspect {
    // 서비스나 컨트롤러의 메소드가 실행될 때마다 로그를 남기기 위한 어드바이스
    @Around("execution(* com.shopping.service..*(..)) || execution(* com.shopping.controller..*(..))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = joinPoint.proceed();
        log.info("{} executed", joinPoint.getSignature().getName());
        return result;
    }
}
