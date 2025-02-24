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

@Service
@RequiredArgsConstructor
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
        if (requestDTO.getSellerId() == null) {
            throw new IllegalArgumentException("ID must not be null");
        }
        Seller seller = sellerRepository.findById(requestDTO.getSellerId())
                .orElseThrow(() -> new SellerNotFoundException("판매자를 찾을 수 없습니다. sellerId: " + requestDTO.getSellerId()));
    
        if (requestDTO.getPrice() <= 0 || requestDTO.getStock() < 0) {
            throw new InvalidProductDataException("가격 또는 재고가 잘못되었습니다.");
        }
    
        Product product = requestDTO.toEntity(seller);
        return productRepository.save(product);
    }
    
}
