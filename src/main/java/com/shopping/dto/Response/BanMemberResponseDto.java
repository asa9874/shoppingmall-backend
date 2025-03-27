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
public class BanMemberResponseDto {
    private Long memberId;
    private String memberName;
    private Long banSeconds;

    public static BanMemberResponseDto fromEntity(Member member, Long banSeconds) {
        return BanMemberResponseDto.builder()
                .memberId(member.getId())
                .memberName(member.getNickname())
                .banSeconds(banSeconds)
                .build();
    }
}
