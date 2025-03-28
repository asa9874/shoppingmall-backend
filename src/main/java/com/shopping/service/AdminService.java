package com.shopping.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.shopping.dto.Response.BanMemberResponseDto;
import com.shopping.model.Member;
import com.shopping.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class AdminService {
    private final RedisTemplate<String, Object> redisTemplate;
    private final MemberRepository memberRepository;

    public void banMember(Long memberId,Long seconds) {
        memberRepository.findById(memberId)
            .orElseThrow(() -> new IllegalArgumentException("해당하는 멤버가 없습니다."));
        redisTemplate.opsForValue().set("ban" + memberId, "ban", seconds, TimeUnit.SECONDS);
    }

    public void unBanMember(Long memberId) {
        if(!redisTemplate.hasKey("ban" + memberId)) {
            throw new IllegalArgumentException("벤되어있지 않은 맴버입니다.");
        }
        redisTemplate.delete("ban" + memberId);
    }

    public List<BanMemberResponseDto> getBanMembers() {
        Set<String> keys = redisTemplate.keys("ban*");
        List<BanMemberResponseDto> banMemberResponseDtoList = new ArrayList<>();
        for (String key : keys) {
            String memberId = key.replace("ban", "");
            Member member = memberRepository.findById(Long.parseLong(memberId))
                .orElseThrow(() -> new IllegalArgumentException("해당하는 멤버가 없습니다."));
            BanMemberResponseDto banMemberResponseDto = BanMemberResponseDto.fromEntity(member, (Long) redisTemplate.getExpire(key, TimeUnit.SECONDS));
            banMemberResponseDtoList.add(banMemberResponseDto);
        }
        return banMemberResponseDtoList;
    }
}
