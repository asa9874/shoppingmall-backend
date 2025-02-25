package com.shopping.service;


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
    //로그인 (JWT 반환)
    public AuthResponseDto login(AuthRequestDto request) {
        Member member = memberRepository.findBymemberId(request.getMemberId())
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        if (!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        String token = jwtTokenProvider.createToken(member.getMemberId(),member.getRole().name());
        return new AuthResponseDto(token);
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

    //제출 아이디 같은지 검사
    //TODO: 이거 PreAuthorize에서 대체 가능하니까 사용한부분 제거하기
    public void validateSameMemberId(String memberId) {
        String currentUserId = SecurityUtil.getCurrentUserId();
        if (currentUserId != memberId) {
            throw new AccessDeniedException(String.format("User ID is not the same as the current logged-in user ID "));
        }
    }
}