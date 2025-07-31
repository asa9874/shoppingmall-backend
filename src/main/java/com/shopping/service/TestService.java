package com.shopping.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TestService {
    private final StringRedisTemplate redisTemplate;
 
    private final RabbitTemplate rabbitTemplate;

    public String testRedisConnection() {
        try {
            redisTemplate.opsForValue().set("testKey", "testValue");
            String value = redisTemplate.opsForValue().get("testKey");
            return (value != null) ? "Redis 연결 성공: " + value : "Redis 연결 실패";
        } catch (Exception e) {
            return "Redis 연결 오류: " + e.getMessage();
        }
    }

    public void sendMessageToRabbitMQ(String message) {
        rabbitTemplate.convertAndSend("taskExchange",  "task.start", message);
        System.out.println("RabbitMQ에 메시지 전송: " + message);
    }
}
