package com.shopping.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.shopping.dto.Request.MemberRegisterRequestDto;
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
    
    //회원가입
    public void register(MemberRegisterRequestDto memberRegisterDto) {
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

    //토큰 기반 닉네임 조회
    public String getNicknameByMemberId(String memberId){
        Member member = memberRepository.findBymemberId(memberId)
            .orElseThrow(() -> new RuntimeException("회원 정보를 찾을 수 없습니다."));
        return member.getNickname();
    }

}
