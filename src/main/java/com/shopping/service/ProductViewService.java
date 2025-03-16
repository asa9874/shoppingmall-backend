package com.shopping.service;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductViewService {
    
    private final String VIEWS_KEY = "product:views";  // DB 업데이트용
    private final String POPULAR_KEY = "product:popular"; // 실시간 인기 제품용
    private final RedisTemplate<String, String> redisTemplate;

    // 조회수 증가
    public void incrementProductView(Long productId) {
        String product = productId.toString(); 
        
        //DB 동기화를 위한 카운트 증가
        redisTemplate.opsForZSet().incrementScore(VIEWS_KEY, product, 1);
        redisTemplate.expire(VIEWS_KEY, 1, TimeUnit.HOURS); // 1시간 유지(근데 5분마다 스케쥴링하며 삭제할거임)
        
        //실시간 인기 제품을 위한 카운트 증가
        redisTemplate.opsForZSet().incrementScore(POPULAR_KEY, product, 1);
        redisTemplate.expire(POPULAR_KEY, 1, TimeUnit.HOURS); // 1시간 유지
    }
}
