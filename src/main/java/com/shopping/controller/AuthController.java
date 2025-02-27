package com.shopping.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopping.dto.Request.AuthRequestDto;
import com.shopping.dto.Response.AuthResponseDto;
import com.shopping.service.AuthService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Tag(name = "인증API", description = "/auth")
public class AuthController {
    private final AuthService authService;

    //로그인 API
    //TODO: Swagger 에 토큰 시간뜨게
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody AuthRequestDto request) {
        AuthResponseDto response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    //TODO: 토큰 시간 초기화
    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken() {
        return null;
    }

    //TODO: 비밀번호 초기화, 이메일 기능추가하기
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword() {
        return null;
    }

    //TODO: 로그아웃
    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        return null;
    }
}
