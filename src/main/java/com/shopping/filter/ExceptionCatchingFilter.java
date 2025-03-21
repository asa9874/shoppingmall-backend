package com.shopping.filter;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Order(Ordered.HIGHEST_PRECEDENCE)
public class ExceptionCatchingFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(ExceptionCatchingFilter.class);

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (RedisConnectionFailureException ex) {
            log.error("Redis connection error: {}", ex.getMessage());
            response.sendError(HttpStatus.SERVICE_UNAVAILABLE.value(), "Redis server is unavailable.");
        } catch (Exception ex) {
            log.error("Unexpected error: {}", ex.getMessage());
            throw ex;
        }
    }
}
