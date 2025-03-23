package com.shopping.listener;

import java.time.Duration;
import java.time.Instant;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/*
 * 이거 에러수준으로 로그 설정해놔서 
 * 서버실행 로그 따로 만든거임 
 * INFO로 설정하면 이미 있는거 뜨니까 이거 그냥 제거해도됨
 */
@Component
public class StartupTimeLogger implements ApplicationListener<ApplicationReadyEvent> {
    private Instant startTime = Instant.now();
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        Instant endTime = Instant.now();
        long timeTaken = Duration.between(startTime, endTime).toMillis(); 
        System.out.println("SpringBoot Started :" + timeTaken + " milliseconds.");
    }
}