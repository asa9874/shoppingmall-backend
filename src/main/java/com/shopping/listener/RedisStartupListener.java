package com.shopping.listener;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class RedisStartupListener implements ApplicationListener<ApplicationReadyEvent> {
    private final RedisConnectionFactory redisConnectionFactory;

    public RedisStartupListener(RedisConnectionFactory redisConnectionFactory) {
        this.redisConnectionFactory = redisConnectionFactory;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        try (RedisConnection connection = redisConnectionFactory.getConnection()) {
            if (connection.ping() != null) {
                log.info("Redis Connection Success");
            } else {
                log.warn("Redis Connection Error");
            }
        } catch (Exception e) {
            log.error("Redis Connet Error");
        }
    }
}
