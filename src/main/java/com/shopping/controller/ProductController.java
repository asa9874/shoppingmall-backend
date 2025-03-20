package com.shopping.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shopping.annotation.RateLimit;
import com.shopping.common.ApiResponse;
import com.shopping.dto.Response.ProductResponseDTO;
import com.shopping.dto.Response.ReviewResponseDto;
import com.shopping.service.ProductService;
import com.shopping.service.ProductViewService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
@Tag(name = "상품API", description = "/product")
@Slf4j
public class ProductController {

    private final ProductService productService;
    private final ProductViewService productViewService;

    @Operation(summary = "상품 목록 조회", description = "상품 목록을 조회합니다.")
    @GetMapping("/")
    public ResponseEntity<ApiResponse<List<ProductResponseDTO>>> getProductItems(@RequestParam(defaultValue = "10") int count) {
        List<ProductResponseDTO> responseDTOs = productService.getProductItems(count);
        ApiResponse<List<ProductResponseDTO>> apiResponse = ApiResponse.success(responseDTOs);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @Operation(summary = "상품 상세 조회", description = "특정 상품의 상세 정보를 조회합니다.")
    @GetMapping("/{productId}")
    @RateLimit(value = 5, timeWindow = 1)
    public ResponseEntity<ApiResponse<ProductResponseDTO>> getProductItemDetail(@PathVariable Long productId) {
        ProductResponseDTO responseDTO = productService.getProductDetail(productId);
        productViewService.incrementProductView(productId);
        ApiResponse<ProductResponseDTO> apiResponse = ApiResponse.success(responseDTO);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @Operation(summary = "상품 리뷰 조회", description = "특정 상품의 리뷰를 조회합니다.")
    @GetMapping("/{productId}/reviews")
    public ResponseEntity<ApiResponse<Page<ReviewResponseDto>>> getProductReviews(
            @PathVariable Long productId, 
            @RequestParam(defaultValue = "0") int page, 
            @RequestParam(defaultValue = "10") int count) {
        Page<ReviewResponseDto> result = productService.getProductReviews(productId, page, count);
        ApiResponse<Page<ReviewResponseDto>> apiResponse = ApiResponse.success(result);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @Operation(summary = "상품 검색", description = "상품을 검색합니다.")
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<Page<ProductResponseDTO>>> searchProducts(
            @RequestParam(required = false) String keyword, 
            @RequestParam(required = false) String category, 
            @RequestParam(required = false) Double minPrice, 
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int count) {
        Page<ProductResponseDTO> result = productService.searchProducts(keyword, category, minPrice, maxPrice, page, count);
        ApiResponse<Page<ProductResponseDTO>> apiResponse = ApiResponse.success(result);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @Operation(summary = "인기 상품 조회", description = "인기 상품 목록을 조회합니다.")
    @GetMapping("/top")
    public ResponseEntity<ApiResponse<List<ProductResponseDTO>>> getPopularProducts() {
        List<ProductResponseDTO> topProducts = productService.getPopularProducts();  
        ApiResponse<List<ProductResponseDTO>> apiResponse = ApiResponse.success(topProducts);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
}
