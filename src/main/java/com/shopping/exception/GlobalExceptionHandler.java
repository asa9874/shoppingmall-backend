package com.shopping.exception;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.RedisSystemException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.shopping.common.ApiResponse;

import io.jsonwebtoken.JwtException;
import io.swagger.v3.oas.annotations.Hidden;
/*
 * 컨트롤러 수준의 예외를 처리하는 클래스
 * 반환으로 ResponseEntity를 사용하여 HTTP 상태 코드와 메시지를 반환
 */
@Hidden
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // Handle SellerNotFoundException
    @ExceptionHandler(SellerNotFoundException.class)
    public ResponseEntity<ApiResponse<String>> handleSellerNotFound(SellerNotFoundException ex) {
        log.error("Seller not found: {}", ex.getMessage());
        ApiResponse<String> apiResponse = ApiResponse.error("Seller not found: " + ex.getMessage(), HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
    }

    // Handle InvalidProductDataException
    @ExceptionHandler(InvalidProductDataException.class)
    public ResponseEntity<ApiResponse<String>> handleInvalidProductData(InvalidProductDataException ex) {
        log.error("Invalid product data: {}", ex.getMessage());
        ApiResponse<String> apiResponse = ApiResponse.error("Invalid product data: " + ex.getMessage(), HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }

    // Handle ProductNotFoundException
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ApiResponse<String>> handleProductNotFoundException(ProductNotFoundException ex) {
        log.error("Product Not Found: {}", ex.getMessage());
        ApiResponse<String> apiResponse = ApiResponse.error("Product Not Found: " + ex.getMessage(), HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }

    // AccessDeniedException
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<String>> handleAccessDeniedException(AccessDeniedException ex) {
        log.error("Access Denied: {}", ex.getMessage());
        ApiResponse<String> apiResponse = ApiResponse.error("Access Denied: " + ex.getMessage(), HttpStatus.FORBIDDEN.value());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(apiResponse);
    }

    // Handle JwtException (JWT 검증 오류 처리)
    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ApiResponse<String>> handleJwtException(JwtException ex) {
        log.error("JWT Error: {}", ex.getMessage());
        ApiResponse<String> apiResponse = ApiResponse.error("Invalid or blacklisted JWT: " + ex.getMessage(), HttpStatus.UNAUTHORIZED.value());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiResponse);
    }

    // 유효성 검사 오류 처리
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        // 필드별 오류 메시지 수집
        String errorMessages = fieldErrors.stream()
            .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
            .collect(Collectors.joining(", "));
        
        ApiResponse<String> apiResponse = ApiResponse.error(errorMessages, HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }

    @ExceptionHandler(RedisSystemException.class)
    public ResponseEntity<ApiResponse<String>> handleRedisException(RedisSystemException ex) {
        log.error("Redis error: {}", ex.getMessage());
        ApiResponse<String> apiResponse = ApiResponse.error("Redis error: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
    }

    @ExceptionHandler(RedisConnectionFailureException.class)
    public ResponseEntity<ApiResponse<String>> handleRedisConnectionFailureException(RedisConnectionFailureException ex) {
        log.error("Redis connection error: {}", ex.getMessage());
        ApiResponse<String> apiResponse = ApiResponse.error("Redis connection error: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
    }

    // Handle generic exceptions (500 Internal Server Error)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<String>> handleUnexpectedError(Exception ex) {
        log.error("Unexpected error: {}", ex.getMessage());
        ApiResponse<String> apiResponse = ApiResponse.error("Unexpected error: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
    }
}
