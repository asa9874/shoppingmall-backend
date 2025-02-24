package com.shopping.controller;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shopping.dto.Request.ProductCreateRequestDTO;
import com.shopping.dto.Response.ProductResponseDTO;
import com.shopping.model.Product;
import com.shopping.service.ProductService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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


    //TODO:상품 상세조회
    @GetMapping("/{productId}")
    public ProductResponseDTO getProductItemDetail(@PathVariable Long productId){
        return null;
    }

    //TODO:상품 목록 검색
    @GetMapping("/search")
    public List<ProductResponseDTO> searchProducts(
        @RequestParam(required = false) String keyword, 
        @RequestParam(required = false) String category, 
        @RequestParam(required = false) Double minPrice, 
        @RequestParam(required = false) Double maxPrice,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int count
    ) {

        return null;
    }


    //관리자 상품추가
    @PostMapping("/create")
    public ResponseEntity<ProductResponseDTO> createProduct(@Valid @RequestBody ProductCreateRequestDTO productCreateRequestDTO) {
        Product product = productService.createProduct(productCreateRequestDTO);
        ProductResponseDTO responseDTO = ProductResponseDTO.fromEntity(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO); // 201 CREATED
    }
    


    //TODO: 관리자 상품수정
    @PutMapping("/modify/{productId}")
    public ResponseEntity<Void> modifyProduct(@PathVariable Long productId, @RequestBody ProductResponseDTO productDTO) {
        return ResponseEntity.status(200).build();
    }

    //TODO: 관리자 상품제거
    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
        return ResponseEntity.status(200).build();
    }


}
