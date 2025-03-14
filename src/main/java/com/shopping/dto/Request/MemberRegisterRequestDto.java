package com.shopping.dto.Request;

import com.shopping.model.Member;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
public class MemberRegisterRequestDto {

    @NotNull(message = "회원 ID는 필수 입력 값입니다.")
    @Size(min = 8, max = 20, message = "회원 ID는 8자 이상 20자 이하로 입력해야 합니다.")
    @Schema(description = "회원 ID", example = "user1234", required = true)
    private String memberId;

    @NotNull(message = "비밀번호는 필수 입력 값입니다.")
    @Size(min = 6, max = 20, message = "비밀번호는 6자 이상 20자 이하로 입력해야 합니다.")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[!@#$%^&*])", message = "새 비밀번호는 숫자, 문자, 특수문자가 포함되어야 합니다.")
    @Schema(description = "비밀번호", example = "password123", required = true)
    private String password;

    @NotNull(message = "닉네임은 필수입니다.")
    @Size(min = 2, max = 30, message = "닉네임은 2자 이상 30자 이하이어야 합니다.")
    @Schema(description = "사용자 닉네임", example = "johnny", required = true)
    private String nickname;

    @NotNull(message = "역할은 필수입니다.")
    @Pattern(regexp = "CUSTOMER|SELLER", message = "역할은 'CUSTOMER', 'SELLER' 중 하나여야 합니다.")
    @Schema(description = "사용자 역할", example = "CUSTOMER", required = true)
    private String role;

    public Member.Role getRole() {
        return Member.Role.valueOf(this.role); 
    }
}
