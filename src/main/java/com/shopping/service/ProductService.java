package com.shopping.service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.shopping.dto.Request.ProductCreateRequestDTO;
import com.shopping.dto.Request.ProductUpdateRequestDto;
import com.shopping.dto.Response.ProductResponseDTO;
import com.shopping.exception.InvalidProductDataException;
import com.shopping.exception.ProductNotFoundException;
import com.shopping.exception.SellerNotFoundException;
import com.shopping.model.Product;
import com.shopping.model.Seller;
import com.shopping.repository.ProductRepository;
import com.shopping.repository.SellerRepository;
import com.shopping.util.ProductUpdateUtil;
import com.shopping.util.ProductValidationUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;
    private final SellerRepository sellerRepository;

    // 조회
    public List<ProductResponseDTO> getProductItems(int count) {
        List<Product> productList = productRepository.findAll();
        if (productList.isEmpty()) {
            return Collections.emptyList();
        }

        List<ProductResponseDTO> productDTOList = productList.stream()
                .limit(count)
                .map(product -> ProductResponseDTO.builder()
                        .id(product.getId())
                        .name(product.getName())
                        .description(product.getDescription())
                        .image("http://localhost:8080/images/" + product.getImage())
                        .price(product.getPrice())
                        .stock(product.getStock())
                        .sellerName(product.getSeller().getMember().getNickname())
                        .category(product.getCategory())
                        .build())
                .collect(Collectors.toList());
        return productDTOList;
    }

    // 생성
    public Product createProduct(ProductCreateRequestDTO requestDTO) {
        Seller seller = sellerRepository.findById(requestDTO.getSellerId())
                .orElseThrow(() -> {
                    String errorMessage = String.format("(sellerId: %d)", requestDTO.getSellerId());
                    return new SellerNotFoundException(errorMessage);
                });
        ProductValidationUtil.validatePriceAndStock(requestDTO.getPrice(), requestDTO.getStock());
        Product product = requestDTO.toEntity(seller);
        return productRepository.save(product);
    }

    // 수정
    public Product updateProduct(Long productId, ProductUpdateRequestDto requestDto) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(String.format("(productId: %d)", productId)));
        ProductValidationUtil.validatePriceAndStock(requestDto.getPrice(), requestDto.getStock());
        ProductUpdateUtil.updateProductFields(product, requestDto);
        return productRepository.save(product);
    }
    
}
