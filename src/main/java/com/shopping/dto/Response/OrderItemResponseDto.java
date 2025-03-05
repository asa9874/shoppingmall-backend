package com.shopping.dto.Response;

import java.time.LocalDateTime;

import com.shopping.model.OrderItem;
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
public class OrderItemResponseDto {
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

    private LocalDateTime orderDate;


    public static OrderItemResponseDto fromEntity(OrderItem orderItem) {
        return OrderItemResponseDto.builder()
            .id(orderItem.getId())
            .productid(orderItem.getProduct().getId())
            .name(orderItem.getProduct().getName())
            .image(orderItem.getProduct().getImage())
            .description(orderItem.getProduct().getDescription())
            .price(orderItem.getProduct().getPrice())
            .stock(orderItem.getProduct().getStock())
            .category(orderItem.getProduct().getCategory())
            .sellerName(orderItem.getProduct().getSeller().getMember().getNickname())  
            .quantity(orderItem.getQuantity())
            .orderDate(orderItem.getOrderDate())
            .build();
    }
}
