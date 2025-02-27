package com.shopping.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopping.dto.Response.CartItemResponseDto;
import com.shopping.dto.Response.OrderItemResponseDto;
import com.shopping.model.CartItem;
import com.shopping.model.Customer;
import com.shopping.service.CustomerService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customer")
@Tag(name = "구매자API", description = "/customer")
@Slf4j
public class CustomerController {
    private final CustomerService customerService;
    
    //TODO: 구매한 상품 리스트 조회
    @GetMapping("/orders")
    public ResponseEntity<OrderItemResponseDto> getOrders() {
        return null;
    }

    // TODO: 특정 상품구입
    @PostMapping("buy-product/{productId}")
    public ResponseEntity<OrderItemResponseDto> buyProduct(@PathVariable Long productId) {
        return null;
    }

    // TODO: 장바구니 상품전체 구입
    @PostMapping("/buy-product")
    public ResponseEntity<OrderItemResponseDto> buyCart() {
        return null;
    }

    // TODO: 장바구니 조회
    @GetMapping("/cart")
    public ResponseEntity<CartItemResponseDto> getCart() {
        List<CartItem> cartItems =customerService.getCart();
        return null;
    }

    // TODO: 장바구니 추가
    @PostMapping("/cart/{productId}")
    public ResponseEntity<CartItemResponseDto> AddCart(@PathVariable Long productId) {
        return null;
    }

    // TODO: 장바구니 상품제거
    @PostMapping("/cart/{productId}")
    public ResponseEntity<Void> deleteCart(@PathVariable Long productId) {
        return null;
    }

}
