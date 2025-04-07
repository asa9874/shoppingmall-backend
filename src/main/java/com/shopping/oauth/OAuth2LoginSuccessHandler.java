package com.shopping.oauth;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.shopping.jwt.JwtTokenProvider;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider; // JWT 유틸 클래스 (직접 만들어야 함)

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, 
                                        HttpServletResponse response,
                                        Authentication authentication) 
                                        throws IOException, ServletException {
        CustomOAuth2User oauthUser = (CustomOAuth2User) authentication.getPrincipal();

        String email = oauthUser.getEmail();
        String role = oauthUser.getAuthorities().iterator().next().getAuthority(); // ex. ROLE_CUSTOMER
        Long id = oauthUser.getMember().getId();
        // JWT 토큰 생성
        String token = jwtTokenProvider.createToken(email, role, id);

        // 응답 헤더에 토큰 추가
        response.setHeader("Authorization", "Bearer " + token);

        // JSON 응답 본문 구성 (프론트에서 토큰 받아서 저장할 수 있게)
        String redirectUrl = "http://localhost:5173/oauth2/redirect?token=" + token;

        response.sendRedirect(redirectUrl);
    }
}