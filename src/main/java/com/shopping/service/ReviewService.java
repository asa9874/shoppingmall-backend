package com.shopping.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shopping.dto.Request.ReviewCreateRequestDto;
import com.shopping.dto.Request.ReviewUpdateRequestDto;
import com.shopping.model.Customer;
import com.shopping.model.Product;
import com.shopping.model.Review;
import com.shopping.repository.CustomerRepository;
import com.shopping.repository.ProductRepository;
import com.shopping.repository.ReviewRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewService {

    private final CustomerRepository customerRepository;
    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;

    public Review getReview(Long reviewId) {
        Review review =reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("해당 리뷰가 존재하지 않습니다."));
        return review;
        }



    public Review createReview(ReviewCreateRequestDto requestDto) {
        Customer customer = customerRepository.findByMemberId(requestDto.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다."));

        Product product = productRepository.findById(requestDto.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("해당 상품이 존재하지 않습니다."));
        
        Review review = requestDto.toEntity(product, customer);
        return reviewRepository.save(review);
    }

    public Review updateReview(Long reviewId,ReviewUpdateRequestDto requestDto) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("해당 리뷰가 존재하지 않습니다."));

        review.setContent(requestDto.getContent());
        review.setRating(requestDto.getRating());
        return reviewRepository.save(review);
    }

    @Transactional
    public void deleteReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("해당 리뷰가 존재하지 않습니다."));
        reviewRepository.delete(review);
    }

}
