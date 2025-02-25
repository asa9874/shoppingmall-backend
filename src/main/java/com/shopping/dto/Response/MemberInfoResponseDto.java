package com.shopping.dto.Response;

import com.shopping.model.Member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberInfoResponseDto {
    private Long id;
    private String memberId;
    private String nickname;
    private Member.Role role;

    public static MemberInfoResponseDto fromEntity(Member member) {
        return MemberInfoResponseDto.builder()
                .id(member.getId())
                .memberId(member.getMemberId())
                .nickname(member.getNickname())
                .role(member.getRole())
                .build();
    }
}
