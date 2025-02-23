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

    private Product.Category category;
}