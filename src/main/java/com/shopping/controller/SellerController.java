package com.shopping.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopping.dto.Request.ProductCreateRequestDTO;
import com.shopping.dto.Request.ProductUpdateRequestDto;
import com.shopping.dto.Response.ProductResponseDTO;
import com.shopping.model.Product;
import com.shopping.service.ProductService;
import com.shopping.service.SellerService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/seller")
@Tag(name = "판매자API", description = "/seller")
@Slf4j
public class SellerController {

    private final SellerService sellerService;
    private final ProductService productService;

    //올린 상품 리스트 조회
    @GetMapping("/{memberId}/products/")
    @PreAuthorize("hasRole('ROLE_ADMIN') or #memberId == authentication.principal.id")
    public ResponseEntity<List<ProductResponseDTO>> getProducts(@PathVariable Long memberId) {
        List<Product> products = sellerService.getProducts(memberId);
        List<ProductResponseDTO> responseDTOs = products.stream()
                .map(ProductResponseDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDTOs);
    }


    //판매자 상품추가
    @PostMapping("/{memberId}/product/create")
    @PreAuthorize("hasRole('ROLE_ADMIN') or #memberId == authentication.principal.id")
    public ResponseEntity<ProductResponseDTO> createProduct(@Valid @PathVariable Long memberId ,@RequestBody ProductCreateRequestDTO productCreateRequestDTO) {
        Product product = productService.createProduct(memberId,productCreateRequestDTO);
        ProductResponseDTO responseDTO = ProductResponseDTO.fromEntity(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO); // 201 CREATED
    }
    
    //판매자 상품수정
    @PutMapping("/{memberId}/product/{productId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or #memberId == authentication.principal.id")
    public ResponseEntity<ProductResponseDTO> updateProduct(@PathVariable Long productId,@PathVariable Long memberId, @RequestBody ProductUpdateRequestDto productResponseDTO) {
        Product product = productService.updateProduct(productId,productResponseDTO);
        ProductResponseDTO responseDTO = ProductResponseDTO.fromEntity(product);
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    //판매자 상품제거
    @DeleteMapping("/{memberId}/product/{productId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or #memberId == authentication.principal.id")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long memberId,@PathVariable Long productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }






}
