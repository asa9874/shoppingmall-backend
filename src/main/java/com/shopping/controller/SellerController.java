package com.shopping.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/seller")
@Tag(name = "판매자API", description = "/seller")
@Slf4j
public class SellerController {

    //TODO: 올린 상품 리스트 조회
    @GetMapping("/products")
    public ResponseEntity<?> getProducts() {
        return null;
    }

    
}
