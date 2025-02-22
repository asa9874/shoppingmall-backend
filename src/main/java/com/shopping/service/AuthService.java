package com.shopping.service;


import com.shopping.dto.Request.AuthRequestDto;
import com.shopping.dto.Response.AuthResponseDto;
import com.shopping.jwt.JwtTokenProvider;
import com.shopping.model.Member;
import com.shopping.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    //로그인 (JWT 반환)
    public AuthResponseDto login(AuthRequestDto request) {
        Member member = memberRepository.findBymemberId(request.getMemberId())
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        if (!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        String token = jwtTokenProvider.createToken(member.getMemberId());
        return new AuthResponseDto(token);
    }

    
}