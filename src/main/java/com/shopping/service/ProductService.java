package com.shopping.service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.shopping.dto.Request.ProductCreateRequestDTO;
import com.shopping.dto.Response.ProductResponseDTO;
import com.shopping.exception.InvalidProductDataException;
import com.shopping.exception.SellerNotFoundException;
import com.shopping.model.Product;
import com.shopping.model.Seller;
import com.shopping.repository.ProductRepository;
import com.shopping.repository.SellerRepository;

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
                String errorMessage = String.format("Seller NOT FOUND sellerId: %d (Seller not found: %d)", requestDTO.getSellerId(), requestDTO.getSellerId());
                log.error(errorMessage); // 로그 출력
                return new SellerNotFoundException(errorMessage);
            });

        // 가격과 재고 검증
        if (requestDTO.getPrice() <= 0 || requestDTO.getStock() < 0) {
            String errorMessage = String.format("STock or Price ERROR (Invalid product data: Price = %d, Stock = %d)", requestDTO.getPrice(), requestDTO.getStock());
            log.error(errorMessage); // 로그 출력
            throw new InvalidProductDataException(errorMessage);
        }
    
        Product product = requestDTO.toEntity(seller);
        return productRepository.save(product);
    }
    
}
