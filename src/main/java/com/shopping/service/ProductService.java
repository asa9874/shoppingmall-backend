package com.shopping.service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shopping.dto.Request.ProductCreateRequestDTO;
import com.shopping.dto.Request.ProductUpdateRequestDto;
import com.shopping.dto.Response.ProductResponseDTO;
import com.shopping.dto.Response.ReviewResponseDto;
import com.shopping.exception.ProductNotFoundException;
import com.shopping.exception.SellerNotFoundException;
import com.shopping.model.Product;
import com.shopping.model.Review;
import com.shopping.model.Seller;
import com.shopping.repository.CartItemRepository;
import com.shopping.repository.OrderItemRepository;
import com.shopping.repository.ProductRepository;
import com.shopping.repository.ReviewRepository;
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
    private final CartItemRepository cartItemRepository;
    private final OrderItemRepository orderItemRepository;
    private final ReviewRepository reviewRepository;

    // 조회
    // TODO : 서비스 전체적 리펙토링 필요
    public List<ProductResponseDTO> getProductItems(int count) {
        List<Product> productList = productRepository.findAll();
        if (productList.isEmpty()) {
            return Collections.emptyList();
        }
        return productList.stream()
                .limit(count)
                .map(ProductResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public Page<ReviewResponseDto> getProductReviews(Long productId, int page, int count) {
        Pageable pageable = PageRequest.of(page, count, Sort.by(Sort.Direction.DESC, "id"));
        Page<Review> reviews = reviewRepository.findByProductId(productId, pageable);
        return reviews.map(ReviewResponseDto::fromEntity);
    }

    public Page<ProductResponseDTO> searchProducts(String keyword, String categorystr, Double minPrice, Double maxPrice,
            int page, int count) {

        Pageable pageable = PageRequest.of(page, count, Sort.by(Sort.Direction.DESC, "id"));
        Product.Category category = Product.fromString(categorystr);

        Page<Product> products = productRepository.searchProducts(
                keyword,
                category,
                minPrice,
                maxPrice,
                pageable);

        return products.map(ProductResponseDTO::fromEntity);
    }

    // 조회
    @Cacheable(value = "product", key = "#productId")
    public ProductResponseDTO getProductDetail(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> {
                    String errorMessage = String.format("(ProductId: %d)", productId);
                    return new ProductNotFoundException(errorMessage);
                });

        return ProductResponseDTO.fromEntity(product);
    }

    // 생성
    public ProductResponseDTO createProduct(Long memberId, ProductCreateRequestDTO requestDTO) {
        Seller seller = sellerRepository.findByMemberId(memberId)
                .orElseThrow(() -> {
                    String errorMessage = String.format("(MemberId: %d)", memberId);
                    return new SellerNotFoundException(errorMessage);
                });
        ProductValidationUtil.validatePriceAndStock(requestDTO.getPrice(), requestDTO.getStock());
        Product product = requestDTO.toEntity(seller);
        seller.getProducts().add(product);
        productRepository.save(product);
        return ProductResponseDTO.fromEntity(product);
    }

    // 수정
    @CachePut(value = "product", key = "#productId")
    public ProductResponseDTO updateProduct(Long productId, ProductUpdateRequestDto requestDto) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> {
                    return new ProductNotFoundException(String.format("(ProductId: %d)", productId));
                });
        // Product product = authService.validateProductOwnership(productId);
        ProductValidationUtil.validatePriceAndStock(requestDto.getPrice(), requestDto.getStock());
        ProductUpdateUtil.updateProductFields(product, requestDto);
        productRepository.save(product);
        return ProductResponseDTO.fromEntity(product);
    }

    // 삭제
    @Transactional
    @CacheEvict(value = "product", key = "#productId")
    public void deleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> {
                    return new ProductNotFoundException(String.format("(ProductId: %d)", productId));
                });
        // Product product = authService.validateProductOwnership(productId);
        cartItemRepository.deleteByProductId(productId);
        orderItemRepository.deleteByProductId(productId);
        productRepository.delete(product);
    }

}
