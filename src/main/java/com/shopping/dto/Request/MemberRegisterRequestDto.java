package com.shopping.dto.Request;

import com.shopping.model.Member;

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
    @NotNull(message = "사용자 ID는 필수입니다.")
    private String memberId;

    @NotNull(message = "비밀번호는 필수입니다.")
    @Size(min = 6, message = "비밀번호는 최소 6자 이상이어야 합니다.")
    private String password;

    @NotNull(message = "닉네임은 필수입니다.")
    private String nickname;

    @NotNull(message = "역할은 필수입니다.")
    @Pattern(regexp = "CUSTOMER|SELLER|BOTH", message = "역할은 'CUSTOMER', 'SELLER', 'BOTH' 중 하나여야 합니다.")
    private String role;

    public Member.Role getRole() {
        return Member.Role.valueOf(this.role); 
    }
}
