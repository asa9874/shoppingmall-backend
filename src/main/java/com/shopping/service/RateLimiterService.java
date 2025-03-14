package com.shopping.service;  // 패키지명

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RateLimiterService { 

    private final RedisTemplate<String, Object> redisTemplate;

    public boolean isAllowed(String clientIp, String endpoint, long requestLimit, long timeWindow) {
        String key = "rate_limit:" + clientIp + ":" + endpoint;

        //요청 횟수 증가
        Long currentCount = redisTemplate.opsForValue().increment(key, 1);
        if(currentCount ==null){
            throw new RuntimeException("Redis error");
        }
        if (currentCount == 1) {
            // 최초 요청 시 TTL 설정
            redisTemplate.expire(key, timeWindow, TimeUnit.SECONDS);
        }

        return currentCount <= requestLimit;
    }

    
}
