package com.shopping.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopping.dto.Request.ReviewCreateRequestDto;
import com.shopping.dto.Response.ReviewResponseDto;
import com.shopping.model.Review;
import com.shopping.service.ReviewService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/review")
@Tag(name = "리뷰API", description = "/review")
public class ReviewController {
    
    private final ReviewService reviewService;

    //TODO: Review CRUD 
    @GetMapping("/")
    public ResponseEntity<List<ReviewResponseDto>> getReviews() {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<ReviewResponseDto> getReview() {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/")
    @PreAuthorize("hasRole('ROLE_ADMIN') or #requestDto.memberId == authentication.principal.id")
    public ResponseEntity<ReviewResponseDto> createReviews(@RequestBody ReviewCreateRequestDto requestDto) {
        Review review =  reviewService.createReview(requestDto);
        ReviewResponseDto responseDto = ReviewResponseDto.fromEntity(review);
        return ResponseEntity.ok(responseDto);
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<ReviewResponseDto> updateReviews(@RequestBody ReviewCreateRequestDto requestDto) {
        return ResponseEntity.ok().build();
    }
    
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReviews() {
        return ResponseEntity.noContent().build();
    }

}
