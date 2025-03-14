package com.shopping.dto.Request;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
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
public class OrderRequestDto {
    @NotNull(message = "주문 수량은 필수 입력 값입니다.")
    @Min(value = 1, message = "주문 수량은 최소 1개 이상이어야 합니다.")
    @Max(value = 1000, message = "주문 수량은 최대 1000개까지 가능합니다.")
    @Schema(description = "주문 수량", example = "2", required = true)
    private Integer quantity;
}
