package com.shopping.service;


import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.shopping.dto.Request.AuthRequestDto;
import com.shopping.dto.Response.AuthResponseDto;
import com.shopping.exception.ProductNotFoundException;
import com.shopping.jwt.JwtTokenProvider;
import com.shopping.model.Member;
import com.shopping.model.Product;
import com.shopping.repository.MemberRepository;
import com.shopping.repository.ProductRepository;
import com.shopping.util.SecurityUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final ProductRepository productRepository;
    private final StringRedisTemplate redisTemplate;

    //로그인 (JWT 반환)
    public AuthResponseDto login(AuthRequestDto request) {
        Member member = memberRepository.findBymemberId(request.getMemberId())
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        if (!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        String token = jwtTokenProvider.createToken(member.getMemberId(),member.getRole().name(),member.getId());
        return new AuthResponseDto(token);
    }

    //토큰 재발급
    public AuthResponseDto refreshAccessToken(String refreshToken) {
        if (refreshToken.startsWith("Bearer ")) {
            refreshToken = refreshToken.substring(7);
        }

        if (!jwtTokenProvider.validateToken(refreshToken)) {
            throw new RuntimeException("Refresh Token이 유효하지 않습니다.");
        }
        String userId = jwtTokenProvider.getUserIdFromToken(refreshToken);
        String role = jwtTokenProvider.getRoleFromToken(refreshToken);
        Long id = jwtTokenProvider.getIdFromToken(refreshToken);
    
        String newAccessToken = jwtTokenProvider.createToken(userId, role, id);

        return new AuthResponseDto(newAccessToken);
    }

    //비번 초기화
    public ResponseEntity<Void> resetPassword(String currentPassword,String newPassword) {
        Member member = memberRepository.findBymemberId(SecurityUtil.getCurrentUserId())
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        
        if (!passwordEncoder.matches(currentPassword, member.getPassword())) {
            throw new RuntimeException("현재 비밀번호가 일치하지 않습니다.");
        }
        
        member.setPassword(passwordEncoder.encode(newPassword));
        memberRepository.save(member);
        return ResponseEntity.ok().build();
    }


    //상품 소유 인증
    public Product validateProductOwnership(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(String.format("(productId: %d)", productId)));

        String currentUserId = SecurityUtil.getCurrentUserId();
        if (!product.isOwnedBy(currentUserId)) {
            throw new AccessDeniedException(String.format("NO OWNER THIS PRODUCTId: %d",productId));
        }
        return product;
    }

    public void logout(String token) {
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        if (!jwtTokenProvider.validateToken(token)) {
            throw new RuntimeException("Token이 유효하지 않습니다.");
        }

        redisTemplate.opsForValue().set("blacklist:" + token, "blacklisted", 1, TimeUnit.HOURS); // TTL 1시간
    }

    /* PreAuthorize 사용함에 따라 안씀
    //제출 아이디 같은지 검사
    public void validateSameMemberId(String memberId) {
        String currentUserId = SecurityUtil.getCurrentUserId();
        if (currentUserId != memberId) {
            throw new AccessDeniedException(String.format("User ID is not the same as the current logged-in user ID "));
        }
    }
    */
}