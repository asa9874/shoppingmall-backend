package com.shopping.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customer")
@Tag(name = "구매자API", description = "/customer")
@Slf4j
public class CustomerController {
    
    //TODO: 구매한 상품 리스트 조회
    @GetMapping("/orders")
    public ResponseEntity<?> getOrders() {
        return null;
    }

    // TODO: 특정 상품구입
    @PostMapping("buy-product/{productid}")
    public ResponseEntity<?> buyProduct() {
        return null;
    }

    // TODO: 장바구니 상품전체 구입
    @PostMapping("/buy-product")
    public ResponseEntity<?> buyCart() {
        return null;
    }

    // TODO: 장바구니 조회
    @GetMapping("/cart")
    public ResponseEntity<?> getCart() {
        return null;
    }

    // TODO: 장바구니 추가
    @PostMapping("/cart")
    public ResponseEntity<?> AddCart() {
        return null;
    }

    // TODO: 장바구니 상품제거
    @PostMapping("/cart/{id}")
    public ResponseEntity<?> deleteCart() {
        return null;
    }

}
