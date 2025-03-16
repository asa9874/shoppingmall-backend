package com.shopping.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.shopping.dto.Request.ProductCreateRequestDTO;
import com.shopping.dto.Request.ProductUpdateRequestDto;
import com.shopping.dto.Response.ProductResponseDTO;
import com.shopping.model.Product;
import com.shopping.service.ProductService;
import com.shopping.service.SellerService;

import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "올린 상품 리스트 조회", description = "판매자가 올린 상품 리스트를 조회합니다.")
    @GetMapping("/{memberId}/products")
    public ResponseEntity<List<ProductResponseDTO>> getProducts(@PathVariable Long memberId,
            @RequestParam(required = false) Integer count) {
        List<Product> products = sellerService.getProducts(memberId, count);
        List<ProductResponseDTO> responseDTOs = products.stream()
                .map(ProductResponseDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDTOs);
    }

    @Operation(summary = "판매자 상품 생성", description = "판매자가 상품을 생성합니다.")
    @PostMapping(value = "/{memberId}/product/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ROLE_ADMIN') or #memberId == authentication.principal.id")
    public ResponseEntity<ProductResponseDTO> createProduct(
            @PathVariable Long memberId,
            @Valid @RequestPart("product") ProductCreateRequestDTO productCreateRequestDTO,
            @RequestPart(value = "image", required = true) MultipartFile imageFile) {

        ProductResponseDTO responseDTO = productService.createProduct(memberId, productCreateRequestDTO, imageFile);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO); // 201 CREATED
    }

    @Operation(summary = "판매자 상품 수정", description = "판매자가 상품을 수정합니다.")
    @PutMapping("/{memberId}/product/{productId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or #memberId == authentication.principal.id")
    public ResponseEntity<ProductResponseDTO> updateProduct(@PathVariable Long productId, @PathVariable Long memberId,
            @Valid @RequestBody ProductUpdateRequestDto productResponseDTO) {
        ProductResponseDTO responseDTO = productService.updateProduct(productId, productResponseDTO);
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    @Operation(summary = "판매자 상품 제거", description = "판매자가 상품을 제거합니다.")
    @DeleteMapping("/{memberId}/product/{productId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or #memberId == authentication.principal.id")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long memberId, @PathVariable Long productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }
}
