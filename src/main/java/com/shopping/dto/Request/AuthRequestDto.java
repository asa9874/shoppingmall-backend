package com.shopping.dto.Request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthRequestDto {

    @NotNull(message = "회원 ID는 필수 입력 값입니다.")
    @Size(min = 8, max = 20, message = "회원 ID는 8자 이상 20자 이하로 입력해야 합니다.")
    @Schema(description = "회원 ID", example = "user1234", required = true)
    private String memberId;

    @NotNull(message = "비밀번호는 필수 입력 값입니다.")
    @Size(min = 6, max = 20, message = "비밀번호는 6자 이상 20자 이하로 입력해야 합니다.")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[!@#$%^&*])", message = "새 비밀번호는 숫자, 문자, 특수문자가 포함되어야 합니다.")
    @Schema(description = "비밀번호", example = "password123", required = true)
    private String password;
}
