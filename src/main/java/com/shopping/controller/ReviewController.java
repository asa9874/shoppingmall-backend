package com.shopping.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopping.dto.Request.ReviewCreateRequestDto;
import com.shopping.dto.Response.ReviewResponseDto;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/review")
@Tag(name = "리뷰API", description = "/review")
public class ReviewController {
    

    //TODO: Review CRUD 
    @GetMapping("/")
    public ResponseEntity<List<ReviewResponseDto>> getReviews() {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<ReviewResponseDto> getReview() {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{reviewId}")
    public ResponseEntity<ReviewResponseDto> createReviews(@RequestBody ReviewCreateRequestDto requestDto) {
        return ResponseEntity.ok().build();
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
