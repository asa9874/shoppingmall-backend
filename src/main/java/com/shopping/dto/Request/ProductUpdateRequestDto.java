package com.shopping.dto.Request;

import com.shopping.model.Product;
import com.shopping.model.Seller;

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
public class ProductUpdateRequestDto {
    private String name;
    
    private String image;
    
    private String description;

    private Integer price;

    private Integer stock;
    
    private Long sellerId;

    private Product.Category category;

    public Product toEntity(Seller seller) {
        return Product.builder()
            .name(this.name)
            .image(this.image)
            .description(this.description)
            .price(this.price)
            .stock(this.stock)
            .category(this.category)
            .seller(seller)  
            .build();
    }
}