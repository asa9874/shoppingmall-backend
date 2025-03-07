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

import com.shopping.dto.Response.ProductResponseDTO;
import com.shopping.dto.Response.ReviewResponseDto;
import com.shopping.service.ProductService;

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

    @GetMapping("/")
    public List<ProductResponseDTO> getProductItems(@RequestParam(defaultValue = "10") int count) {
        return productService.getProductItems(count);
    }


    //상품 상세조회
    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponseDTO> getProductItemDetail(@PathVariable Long productId){
        ProductResponseDTO responseDTO = productService.getProductDetail(productId);
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    @GetMapping("/{productId}/reviews")
    public ResponseEntity<Page<ReviewResponseDto>> getProductReviews(@PathVariable Long productId, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int count) {
        Page<ReviewResponseDto> result = productService.getProductReviews(productId, page, count);
        return ResponseEntity.ok(result);
    }

    //상품 목록 검색
    @GetMapping("/search")
    public ResponseEntity<Page<ProductResponseDTO>> searchProducts(
        @RequestParam(required = false) String keyword, 
        @RequestParam(required = false) String category, 
        @RequestParam(required = false) Double minPrice, 
        @RequestParam(required = false) Double maxPrice,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int count
    ) {
        Page<ProductResponseDTO> result = productService.searchProducts(keyword, category, minPrice, maxPrice, page, count);
        return ResponseEntity.ok(result);
    }
}
