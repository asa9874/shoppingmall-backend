package com.shopping.controller;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shopping.service.ProductService;
import com.shopping.dto.ProductDTO;

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
}
