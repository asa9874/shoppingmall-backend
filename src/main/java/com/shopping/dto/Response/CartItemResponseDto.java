package com.shopping.dto.Response;

import com.shopping.model.CartItem;
import com.shopping.model.Product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItemResponseDto {
    private Long id;

    private Long productid;

    private String name;
    
    private String image;
    
    private String description;

    private int price;

    private int stock;

    private String sellerName;

    private Product.Category category;

    private int quantity; 

    public static CartItemResponseDto fromEntity(CartItem cartItem) {
        return CartItemResponseDto.builder()
            .id(cartItem.getId())
            .productid(cartItem.getProduct().getId())
            .name(cartItem.getProduct().getName())
            .image(cartItem.getProduct().getImage())
            .description(cartItem.getProduct().getDescription())
            .price(cartItem.getProduct().getPrice())
            .stock(cartItem.getProduct().getStock())
            .category(cartItem.getProduct().getCategory())
            .sellerName(cartItem.getProduct().getSeller().getMember().getNickname())  
            .quantity(cartItem.getQuantity())
            .build();
    }
}
