package com.shopping.service;  // 패키지명

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RateLimiterService { //TODO: 이거 클래스명 변경해야할듯

    private final RedisTemplate<String, Object> redisTemplate;

    public boolean isAllowed(String clientIp, String endpoint, long requestLimit, long timeWindow) {
        String key = "rate_limit:" + clientIp + ":" + endpoint;

        //요청 횟수 증가
        Long currentCount = redisTemplate.opsForValue().increment(key, 1);

        if (currentCount == 1) {
            // 최초 요청 시 TTL 설정
            redisTemplate.expire(key, timeWindow, TimeUnit.SECONDS);
        }

        return currentCount <= requestLimit;
    }

    // 조회수 증가
    public void incrementProductView(Long productId) {
        String key = "product:views";
        String product = productId.toString(); 

        // 조회수 증가
        redisTemplate.opsForZSet().incrementScore(key, product, 1);
        
        //1시간(3600초) 동안만 유지
        redisTemplate.expire(key, 1, TimeUnit.HOURS);
    }
}
