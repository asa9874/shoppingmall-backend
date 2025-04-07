package com.shopping.oauth;

import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.shopping.model.Member;

// CustomOAuth2User.java
public class CustomOAuth2User implements OAuth2User {

    private Member member;
    private Map<String, Object> attributes;
    
    public CustomOAuth2User(Member member, Map<String, Object> attributes) {
        this.member = member;
        this.attributes = attributes;
    }
    
    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Member 엔티티의 role 값을 활용하여 권한 부여 (예: ROLE_CUSTOMER, ROLE_SELLER, ROLE_ADMIN)
        return AuthorityUtils.createAuthorityList("ROLE_" + member.getRole().name());
    }
    
    @Override
    public String getName() {
        // Spring Security 내부에서 사용하는 이름 정보 (예시로 nickname 사용)
        return member.getNickname();
    }
    
    // 추가로 필요한 사용자 정보를 메소드로 제공
    public String getEmail() {
        return member.getMemberId();
    }
}
