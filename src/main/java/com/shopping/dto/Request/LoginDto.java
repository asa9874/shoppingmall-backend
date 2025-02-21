package com.shopping.dto.Request;

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
public class LoginDto {
    @NotNull(message = "아이디 필수")
    String memberId;
    
    @NotNull(message = "비번 필수")
    String password;
}
