package com.shopping.service;

import java.util.Map;
import java.util.UUID;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.shopping.model.Member;
import com.shopping.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final MemberRepository memberRepository;
    
    
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // 기본 OAuth2UserService를 이용하여 사용자 정보 가져오기
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);
        
        // provider 정보 추출 (ex: google)
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        
        // OAuth2 공급자마다 사용자 정보의 key가 다를 수 있음.
        // Google의 경우 "email"로 제공됨.
        Map<String, Object> attributes = oAuth2User.getAttributes();
        String email = (String) attributes.get("email");
        
        // 이메일을 기준으로 기존 회원 검색
        Member member = memberRepository.findBymemberId(email)
            .orElseGet(() -> {
                // 신규 사용자라면 회원가입 처리
                Member newMember = new Member();
                newMember.setMemberId(email);
                newMember.setNickname((String) attributes.get("name")); // 제공되는 이름 정보
                newMember.setProvider(registrationId);
                // OAuth2 인증 사용자는 비밀번호를 사용하지 않으므로 임의의 비밀번호 값 설정 (예: UUID)
                newMember.setPassword(UUID.randomUUID().toString());
                // 기본 Role 설정 (예: CUSTOMER)
                newMember.setRole(Member.Role.CUSTOMER);
                return memberRepository.save(newMember);
            });
        
        // 필요한 경우 기존 회원의 정보 업데이트 가능
        // 예를 들어, 이름이 바뀌었다면 업데이트하는 로직 추가 가능
        
        // 커스텀 OAuth2User 객체로 변환하여 반환
        return new CustomOAuth2User(user, attributes);
    }
}