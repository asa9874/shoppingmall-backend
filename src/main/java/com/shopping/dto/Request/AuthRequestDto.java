package com.shopping.dto.Request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthRequestDto {
    private String memberId;
    private String password;
}
