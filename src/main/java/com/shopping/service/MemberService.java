package com.shopping.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.shopping.dto.Request.MemberRegisterDto;
import com.shopping.model.Member;
import com.shopping.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {
    
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomerService customerService;
    private final SellerService sellerService;
    
    public void register(MemberRegisterDto memberRegisterDto) {
        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(memberRegisterDto.getPassword());

        // 사용자 정보 저장
        Member member = Member.builder()
            .memberId(memberRegisterDto.getMemberId())
            .password(encodedPassword) 
            .nickname(memberRegisterDto.getNickname())
            .role(memberRegisterDto.getRole())
            .build();
        memberRepository.save(member);

        if (member.getRole() == Member.Role.CUSTOMER) {
            customerService.registerCustomer(member);  
        }

        if (member.getRole() == Member.Role.SELLER) {
            sellerService.registerSeller(member);  
        }
    }

}
