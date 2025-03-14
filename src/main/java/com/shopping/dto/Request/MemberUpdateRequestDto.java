package com.shopping.dto.Request;

import io.swagger.v3.oas.annotations.media.Schema;
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
public class MemberUpdateRequestDto {
    @NotNull(message = "닉네임은 필수입니다.")
    @Size(min = 2, max = 30, message = "닉네임은 2자 이상 30자 이하이어야 합니다.")
    @Schema(description = "사용자 닉네임", example = "johnny", required = true)
    String nickname;
}
