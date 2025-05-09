package com.shopping.dto.Request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
public class CartRequestDto {
    @Min(value = 1, message = "수량은 1 이상이어야 합니다.")
    @Max(value = 100, message = "수량은 100 이하로 입력해야 합니다.")
    @Schema(description = "상품 수량", example = "2", required = true)
    private Integer quantity;
}
