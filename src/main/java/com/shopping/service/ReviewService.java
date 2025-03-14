package com.shopping.service;

import java.util.List;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shopping.dto.Request.ReviewCreateRequestDto;
import com.shopping.dto.Request.ReviewUpdateRequestDto;
import com.shopping.dto.Response.ReviewResponseDto;
import com.shopping.model.Customer;
import com.shopping.model.Product;
import com.shopping.model.Review;
import com.shopping.repository.CustomerRepository;
import com.shopping.repository.ProductRepository;
import com.shopping.repository.ReviewRepository;
import com.shopping.util.SecurityUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewService {

    private final CustomerRepository customerRepository;
    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;

    public List<Review> getReviews() {
        List<Review> reviews = reviewRepository.findAll();
        return reviews;
    }

    public ReviewResponseDto getReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("해당 리뷰가 존재하지 않습니다."));
        ReviewResponseDto responseDto = ReviewResponseDto.fromEntity(review);
        return responseDto;
    }

    public ReviewResponseDto createReview(ReviewCreateRequestDto requestDto, Long memberId) {
        Customer customer = customerRepository.findByMemberId(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다."));

        Product product = productRepository.findById(requestDto.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("해당 상품이 존재하지 않습니다."));

        Review review = requestDto.toEntity(product, customer);
        reviewRepository.save(review);
        ReviewResponseDto responseDto = ReviewResponseDto.fromEntity(review);
        return responseDto;
    }

    public ReviewResponseDto updateReview(Long reviewId, ReviewUpdateRequestDto requestDto, Long memberId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("해당 리뷰가 존재하지 않습니다."));
        if (!SecurityUtil.isAdminOrOwner(review.getCustomer().getMember().getId(), memberId)) {
            throw new AccessDeniedException("해당 리뷰를 업데이트할 권한이 없습니다.");
        }
        review.setContent(requestDto.getContent());
        review.setRating(requestDto.getRating());
        reviewRepository.save(review);
        ReviewResponseDto responseDto = ReviewResponseDto.fromEntity(review);
        return responseDto;
    }

    @Transactional
    public void deleteReview(Long reviewId, Long memberId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("해당 리뷰가 존재하지 않습니다."));
        if (!SecurityUtil.isAdminOrOwner(review.getCustomer().getMember().getId(), memberId)) {
            throw new AccessDeniedException("해당 리뷰를 업데이트할 권한이 없습니다.");
        }
        reviewRepository.delete(review);
    }

}
