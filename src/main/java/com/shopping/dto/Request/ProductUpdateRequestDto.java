package com.shopping.dto.Request;

import com.shopping.model.Product;
import com.shopping.model.Seller;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    @NotBlank(message = "상품명은 필수 입력값입니다.")
    @Size(min = 2, max = 100, message = "상품명은 최소 2자 이상, 최대 100자 이하로 입력해야 합니다.")
    @Schema(description = "상품명", example = "무선 이어폰", required = true)
    private String name;
    
    @Schema(description = "상품 이미지 URL", example = "https://example.com/product.jpg")
    private String image;
    
    @NotBlank(message = "상품 설명은 필수 입력값입니다.")
    @Size(max = 500, message = "상품 설명은 최대 500자까지 입력 가능합니다.")
    @Schema(description = "상품 설명", example = "고음질 무선 이어폰입니다.", required = true)
    private String description;


    @NotNull(message = "상품 가격은 필수 입력값입니다.")
    @Min(value = 0, message = "상품 가격은 0원 이상이어야 합니다.")
    @Schema(description = "상품 가격", example = "129000", required = true)
    private Integer price;

    @NotNull(message = "재고 수량은 필수 입력값입니다.")
    @Min(value = 0, message = "재고 수량은 0개 이상이어야 합니다.")
    @Schema(description = "재고 수량", example = "50", required = true)
    private Integer stock;
    
    @NotNull(message = "상품 카테고리는 필수 입력값입니다.")
    @Schema(description = "상품 카테고리", example = "ELECTRONICS", required = true)
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