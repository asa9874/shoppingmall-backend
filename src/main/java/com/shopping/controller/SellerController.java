package com.shopping.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopping.dto.Response.ProductResponseDTO;
import com.shopping.model.Product;
import com.shopping.service.SellerService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/seller")
@Tag(name = "판매자API", description = "/seller")
@Slf4j
public class SellerController {

    private final SellerService sellerService;

    //올린 상품 리스트 조회
    @GetMapping("/{memberId}/products/")
    @PreAuthorize("hasRole('ROLE_ADMIN') or #id == authentication.principal.id")
    public ResponseEntity<List<ProductResponseDTO>> getProducts(@PathVariable Long memberId) {
        List<Product> products = sellerService.getProducts(memberId);
        List<ProductResponseDTO> responseDTOs = products.stream()
                .map(ProductResponseDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDTOs);
    }

}
