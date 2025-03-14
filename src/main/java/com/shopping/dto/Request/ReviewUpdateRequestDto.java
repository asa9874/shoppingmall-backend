package com.shopping.dto.Request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
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
public class ReviewUpdateRequestDto {
    
    @NotBlank(message = "리뷰 내용은 필수 입력값입니다.")
    @Size(min = 10, max = 1000, message = "리뷰 내용은 최소 10자 이상, 최대 1000자 이하로 입력해야 합니다.")
    @Schema(description = "리뷰 내용", example = "이 제품 정말 마음에 들어요! 품질이 좋고 배송도 빨랐어요.", required = true)
    private String content;
    
    @NotNull(message = "별점은 필수 입력값입니다.")
    @Min(value = 1, message = "별점은 최소 1점 이상이어야 합니다.")
    @Max(value = 5, message = "별점은 최대 5점 이하여야 합니다.")
    @Schema(description = "별점 (1~5)", example = "5", required = true)
    private Long rating;
}
