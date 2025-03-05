package com.shopping.dto.Request;

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
    private int productId;
    private int memeberId;
    private String content;
    private int rating;
}
