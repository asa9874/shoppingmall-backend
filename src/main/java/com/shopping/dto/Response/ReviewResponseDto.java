package com.shopping.dto.Response;

import com.shopping.model.Review;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewResponseDto {
    private Long id;
    private String productName;
    private String customerName;
    private String content;
    private Long rating;


    public static ReviewResponseDto fromEntity(Review review) {
        return ReviewResponseDto.builder()
            .id(review.getId())
            .productName(review.getProduct().getName())
            .customerName(review.getCustomer().getMember().getNickname())
            .content(review.getContent())
            .rating(review.getRating())
            .build();
    }
}
