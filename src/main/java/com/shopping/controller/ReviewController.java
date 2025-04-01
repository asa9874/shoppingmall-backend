package com.shopping.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopping.common.ApiResponse;
import com.shopping.dto.Request.ReviewCreateRequestDto;
import com.shopping.dto.Request.ReviewUpdateRequestDto;
import com.shopping.dto.Response.ReviewResponseDto;
import com.shopping.jwt.CustomUserDetails;
import com.shopping.model.Review;
import com.shopping.service.ReviewService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequiredArgsConstructor
@RequestMapping("/review")
@Log4j2
@Tag(name = "리뷰API", description = "/review")
public class ReviewController {

    private final ReviewService reviewService;

    @Operation(summary = "리뷰 목록 조회", description = "모든 리뷰를 조회합니다.")
    @GetMapping("/")
    public ResponseEntity<ApiResponse<List<ReviewResponseDto>>> getReviews() {
        List<Review> reviews = reviewService.getReviews();
        List<ReviewResponseDto> responseDto = reviews.stream()
                .map(ReviewResponseDto::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(responseDto));
    }

    @Operation(summary = "리뷰 조회", description = "특정 리뷰를 조회합니다.")
    @GetMapping("/{reviewId}")
    public ResponseEntity<ApiResponse<ReviewResponseDto>> getReview(@PathVariable Long reviewId) {
        ReviewResponseDto responseDto = reviewService.getReview(reviewId);
        return ResponseEntity.ok(ApiResponse.success(responseDto));
    }

    @Operation(summary = "리뷰 생성", description = "새로운 리뷰를 생성합니다.")
    @PostMapping
    public ResponseEntity<ApiResponse<ReviewResponseDto>> createReviews(
            @Valid @RequestBody ReviewCreateRequestDto requestDto,
            @AuthenticationPrincipal CustomUserDetails member) {
        ReviewResponseDto responseDto = reviewService.createReview(requestDto, member.getId());
        return ResponseEntity.ok(ApiResponse.success(responseDto));
    }

    @Operation(summary = "리뷰 업데이트", description = "특정 리뷰를 업데이트합니다.(본인 권한)")
    @PutMapping("/{reviewId}")
    public ResponseEntity<ApiResponse<ReviewResponseDto>> updateReviews(
            @PathVariable Long reviewId,
            @Valid @RequestBody ReviewUpdateRequestDto requestDto,
            @AuthenticationPrincipal CustomUserDetails member) {
        ReviewResponseDto responseDto = reviewService.updateReview(reviewId, requestDto, member.getId());
        return ResponseEntity.ok(ApiResponse.success(responseDto));
    }

    @Operation(summary = "리뷰 삭제", description = "특정 리뷰를 삭제합니다.(본인 권한)")
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReviews(@PathVariable Long reviewId,
            @AuthenticationPrincipal CustomUserDetails member) {
        reviewService.deleteReview(reviewId, member.getId());
        return ResponseEntity.noContent().build();
    }
}
