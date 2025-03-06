package com.shopping.dto.Request;

import com.shopping.model.Customer;
import com.shopping.model.Product;
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
public class ReviewCreateRequestDto {
    private Long productId;
    private Long memberId;
    private String content;
    private Long rating;

    public Review toEntity(Product product, Customer customer) {
        return Review.builder()
                .product(product)
                .customer(customer)
                .content(this.content)
                .rating(this.rating)
                .build();
    }
}
