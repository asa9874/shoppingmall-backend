package com.shopping.aspect;  // 패키지명

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.shopping.annotation.RateLimit;
import com.shopping.service.RateLimiterService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Aspect
@Component
@RequiredArgsConstructor
public class RateLimitAspect {

    private final RateLimiterService rateLimiterService;

    @Around("@annotation(rateLimit)")  // @RateLimit이 적용된 메서드만 적용
    public Object checkRateLimit(ProceedingJoinPoint joinPoint, RateLimit rateLimit) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            throw new IllegalStateException("No request context available");
        }

        HttpServletRequest request = attributes.getRequest();
        String clientIp = request.getRemoteAddr();
        String endpoint = request.getRequestURI();

        // 요청 제한 확인
        if (!rateLimiterService.isAllowed(clientIp, endpoint, rateLimit.value(), rateLimit.timeWindow())) {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body("Too many requests");
        }

        return joinPoint.proceed();
    }
}
