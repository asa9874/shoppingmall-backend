package com.shopping.scheduler;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.shopping.model.Product;
import com.shopping.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProductViewScheduler {

    private final String REDIS_KEY = "product:views";
    private final RedisTemplate<String, Object> redisTemplate;
    private final ProductRepository productRepository;

    // 5분마다 Redis 조회수를 DB에 반영
    @Scheduled(fixedRate = 100000)
    public void syncViewCountsToDatabase() {
        try {
            log.info("product:views syncViewCountsToDatabase ");
            Set<String> productIds = Optional.ofNullable(redisTemplate.opsForZSet().range(REDIS_KEY, 0, -1)) 
                    .orElse(Collections.emptySet())
                    .stream()
                    .map(Object::toString)
                    .collect(Collectors.toSet());

            if (productIds == null || productIds.isEmpty()) {
                return;
            }

            for (String productId : productIds) {
                // Redis에서 해당 제품의 조회수를 가져옴
                Double score = redisTemplate.opsForZSet().score(REDIS_KEY, productId);

                if (score != null && score > 0) {
                    Long id = Long.parseLong(productId);

                    // DB에서 기존 조회수 가져오기
                    Product product = productRepository.findById(id).orElse(null);
                    if (product != null) {
                        product.setViewCount(product.getViewCount() + score.intValue());
                        productRepository.save(product);
                    }

                    // Redis에서 해당 제품의 조회수 삭제
                    redisTemplate.opsForZSet().remove(REDIS_KEY, productId);
                }
            }
        } catch (Exception e) {
            log.error("Failed to sync view counts to database");
        }
    }
}