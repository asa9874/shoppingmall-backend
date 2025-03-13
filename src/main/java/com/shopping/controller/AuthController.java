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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Tag(name = "인증API", description = "/auth")
public class AuthController {
    private final AuthService authService;

    @Operation(summary = "로그인", description = "사용자가 로그인합니다.")
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody AuthRequestDto request) {
        AuthResponseDto response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "토큰 갱신", description = "리프레시 토큰을 사용하여 액세스 토큰을 갱신합니다.")
    @PostMapping("/refresh-token")
    public ResponseEntity<AuthResponseDto> refreshToken(@RequestHeader("Authorization") String refreshToken) {
        AuthResponseDto response = authService.refreshAccessToken(refreshToken);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "비밀번호 초기화", description = "사용자의 비밀번호를 초기화합니다.")
    @PostMapping("/reset-password")
    public ResponseEntity<Void> resetPassword(@RequestBody AuthResetPasswordRequestDto requestDto) {
        return authService.resetPassword(requestDto.getCurrentPassword(), requestDto.getNewPassword());
    }

    @Operation(summary = "로그아웃", description = "사용자가 로그아웃합니다.")
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader("Authorization") String token) {
        authService.logout(token);
        return ResponseEntity.noContent().build();
    }
}
