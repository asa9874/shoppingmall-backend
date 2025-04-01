package com.shopping.listener;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.Instant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
@RequiredArgsConstructor
public class StartupHealthCheckListener implements ApplicationListener<ApplicationReadyEvent> {
    Instant startTime = Instant.now();
    private final RedisConnectionFactory redisConnectionFactory;

    @Value("${grafana.url}")  
    private String grafanaUrl;

    @Value("${prometheus.url}") 
    private String prometheusUrl;

    @Override
    public void onApplicationEvent(@NonNull ApplicationReadyEvent event) {
        checkRedisConnection();
        checkGrafanaConnection();
        checkPrometheusConnection();
        checkStartupTime();
    }

    // Redis 연결 체크
    private void checkRedisConnection() {
        try (RedisConnection connection = redisConnectionFactory.getConnection()) {
            if (connection.ping() != null) {
                log.info("Redis Connection Success");
            } else {
                log.warn("Redis Connection Error");
            }
        } catch (Exception e) {
            log.error("Redis Connection Error");
        }
    }

    // Grafana 연결 체크
    private void checkGrafanaConnection() {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String url = grafanaUrl + "/api/health";  
            String response = restTemplate.getForObject(url, String.class);

            if (response != null && response.contains("status\":\"ok")) {
                log.info("Grafana Connection Success");
            } else {
                log.warn("Grafana Connection Error");
            }
        } catch (Exception e) {
            log.error("Grafana Connection Error");
        }
    }

    // Prometheus 연결 체크
    private void checkPrometheusConnection() {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(prometheusUrl + "/metrics"))  
                .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                log.info("Prometheus Connection Success");
            } else {
                log.warn("Prometheus Connection Error");
            }
        } catch (Exception e) {
            log.error("Prometheus Connection Error");
        }
    }

    // Spring Boot 시작 시간 체크
    private void checkStartupTime() {
        Instant endTime = Instant.now();
        long timeTaken = Duration.between(startTime, endTime).toMillis();
        System.out.println("SpringBoot Started: " + timeTaken + " milliseconds.");
    }
}

