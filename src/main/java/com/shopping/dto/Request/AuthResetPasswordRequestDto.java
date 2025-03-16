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
public class AuthResetPasswordRequestDto {
    
    @NotNull(message = "현재 비밀번호는 필수 입력 값입니다.")
    @Size(min = 6, max = 20, message = "현재 비밀번호는 6자 이상 20자 이하로 입력해야 합니다.")
    @Schema(description = "현재 비밀번호", example = "currentPassword123@", required = true)
    private String currentPassword; 

    @NotNull(message = "새 비밀번호는 필수 입력 값입니다.")
    @Size(min = 6, max = 20, message = "새 비밀번호는 6자 이상 20자 이하로 입력해야 합니다.")
    @Schema(description = "새 비밀번호", example = "newPassword123@", required = true)
    private String newPassword; 
}
