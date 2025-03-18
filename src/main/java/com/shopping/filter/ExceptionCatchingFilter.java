package com.shopping.filter;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Order(Ordered.HIGHEST_PRECEDENCE)
public class ExceptionCatchingFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(ExceptionCatchingFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                    HttpServletResponse response, 
                                    FilterChain filterChain)
                                    throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (RedisConnectionFailureException ex) {
            // 간략화된 메시지로 로깅
            log.error("Redis connection error: {}", ex.getMessage());
            // 사용자에게 짧은 메시지 반환
            response.sendError(HttpStatus.SERVICE_UNAVAILABLE.value(), "Redis server is unavailable.");
        } catch (Exception ex) {
            // 그 외의 예외는 다시 던짐(또는 여기서 처리)
            log.error("Unexpected error: {}", ex.getMessage());
            throw ex;
        }
    }
}
