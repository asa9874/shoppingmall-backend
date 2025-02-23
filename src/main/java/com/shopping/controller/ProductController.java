package com.shopping.controller;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shopping.dto.Response.ProductDTO;
import com.shopping.service.ProductService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
@Tag(name = "상품API", description = "/product")
public class ProductController {
    
    private final ProductService productService;

    @GetMapping("/")
    public List<ProductDTO> getProductItems(@RequestParam(defaultValue = "10") int count) {
        return productService.getProductItems(count);
    }


    //TODO:상품 상세조회
    @GetMapping("/{productId}")
    public ProductDTO getProductItemDetail(@PathVariable Long productId){
        return null;
    }

    //TODO:상품 목록 검색
    @GetMapping("/search")
    public List<ProductDTO> searchProducts(
        @RequestParam(required = false) String keyword, 
        @RequestParam(required = false) String category, 
        @RequestParam(required = false) Double minPrice, 
        @RequestParam(required = false) Double maxPrice,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int count
    ) {

        return null;
    }


    //----DTO는 ProductDTO 아님 임의로 넣어둔거임
    //TODO: 관리자 상품추가
    @PostMapping("/Create")
    public ResponseEntity<Void> createProduct(@RequestBody ProductDTO productDTO) {
        return ResponseEntity.status(200).build();
    }


    //TODO: 관리자 상품수정
    @PutMapping("/modify/{productId}")
    public ResponseEntity<Void> modifyProduct(@PathVariable Long productId, @RequestBody ProductDTO productDTO) {
        return ResponseEntity.status(200).build();
    }

    //TODO: 관리자 상품제거
    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
        return ResponseEntity.status(200).build();
    }


}
