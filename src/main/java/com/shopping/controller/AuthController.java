package com.shopping.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopping.dto.Request.AuthRequestDto;
import com.shopping.dto.Request.AuthResetPasswordRequestDto;
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
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody AuthRequestDto request) {
        AuthResponseDto response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    //토큰 시간 초기화
    @PostMapping("/refresh-token")
    public ResponseEntity<AuthResponseDto> refreshToken(@RequestHeader("Authorization") String refreshToken) {
        AuthResponseDto response = authService.refreshAccessToken(refreshToken);
        return ResponseEntity.ok(response);
    }

    //비밀번호 초기화, 
    //TODO: 이메일 기능추가하기
    @PostMapping("/reset-password")
    public ResponseEntity<Void> resetPassword(@RequestBody AuthResetPasswordRequestDto requestDto) {
        return authService.resetPassword(requestDto.getCurrentPassword(), requestDto.getNewPassword());
    }

    //TODO: 로그아웃
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader("Authorization") String token) {
        authService.logout(token);
        return ResponseEntity.noContent().build();
    }
}
