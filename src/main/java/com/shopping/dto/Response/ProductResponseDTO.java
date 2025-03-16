package com.shopping.dto.Response;

import com.shopping.model.Product;

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
public class ProductResponseDTO {
    private Long id;

    private String name;
    
    private String image;
    
    private String description;

    private int price;

    private int stock;

    private String sellerName;

    private Long memberId;

    private Product.Category category;

    private int viewCount;

    public static ProductResponseDTO fromEntity(Product product) {
        return ProductResponseDTO.builder()
            .id(product.getId())
            .name(product.getName())
            .image(product.getImage())
            .description(product.getDescription())
            .price(product.getPrice())
            .stock(product.getStock())
            .category(product.getCategory())
            .sellerName(product.getSeller().getMember().getNickname())  
            .memberId(product.getSeller().getMember().getId())
            .viewCount(product.getViewCount())
            .build();
    }
}