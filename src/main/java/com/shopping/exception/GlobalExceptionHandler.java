package com.shopping.exception;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.RedisSystemException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import io.jsonwebtoken.JwtException;
import io.swagger.v3.oas.annotations.Hidden;

@Hidden
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // Handle SellerNotFoundException
    @ExceptionHandler(SellerNotFoundException.class)
    public ResponseEntity<String> handleSellerNotFound(SellerNotFoundException ex) {
        log.error("Seller not found: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Seller not found: " + ex.getMessage());
    }

    // Handle InvalidProductDataException
    @ExceptionHandler(InvalidProductDataException.class)
    public ResponseEntity<String> handleInvalidProductData(InvalidProductDataException ex) {
        log.error("Invalid product data: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid product data: " + ex.getMessage());
    }
    
    // Handle ProductNotFoundException
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<String> handleProductNotFoundException(ProductNotFoundException ex) {
        log.error("Product Not Found: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Product Not Found" + ex.getMessage());
    }

    // AccessDeniedException
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> handleAccessDeniedException(AccessDeniedException ex) {
        log.error("Access Denied: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access Denied: " + ex.getMessage());
    }

    // Handle JwtException (JWT 검증 오류 처리)
    @ExceptionHandler(JwtException.class)
    public ResponseEntity<String> handleJwtException(JwtException ex) {
        log.error("JWT Error: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or blacklisted JWT: " + ex.getMessage());

    }

    // 유효성 검사 오류 처리
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        // 필드별 오류 메시지 수집
        String errorMessages = fieldErrors.stream()
            .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
            .collect(Collectors.joining(", "));
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessages);
    }

    @ExceptionHandler(RedisSystemException.class)
    public ResponseEntity<String> handleRedisException(RedisSystemException ex) {
        log.error("Redis error: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Redis error: " + ex.getMessage());
    }

    @ExceptionHandler(RedisConnectionFailureException.class)
    public ResponseEntity<String> handleRedisConnectionFailureException(RedisConnectionFailureException ex) {
        log.error("Redis connection error: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Redis connection error: " + ex.getMessage());
    }


    // Handle generic exceptions (500 Internal Server Error)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleUnexpectedError(Exception ex) {
        log.error("Unexpected error: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error: " + ex.getMessage());
    }

    
}
