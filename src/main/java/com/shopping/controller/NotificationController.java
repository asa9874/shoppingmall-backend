package com.shopping.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopping.dto.Request.NotificationCreateRequestDto;
import com.shopping.dto.Request.NotificationUpdateRequestDto;
import com.shopping.dto.Response.NotificationResponseDto;
import com.shopping.service.NotificationService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/notification")
@Tag(name = "알림API", description = "/notification")
public class NotificationController {

    private final NotificationService notificationService;

    // 조회
    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<NotificationResponseDto>> getNotifications() {
        return ResponseEntity.ok(notificationService.getNotifications());
    }

    // 상세조회
    @GetMapping("/{notificationId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<NotificationResponseDto> getNotification(@PathVariable Long notificationId) {
        return ResponseEntity.ok(notificationService.getNotification(notificationId));
    }

    // 생성
    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<NotificationResponseDto> createNotification(
            @RequestBody NotificationCreateRequestDto requestDto) {
        return ResponseEntity.ok(notificationService.createNotification(requestDto));
    }

    // 업데이트
    @PutMapping("/{notificationId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<NotificationResponseDto> updateNotification(@PathVariable Long notificationId,
            @RequestBody NotificationUpdateRequestDto requestDto) {
        return ResponseEntity.ok(notificationService.updateNotification(notificationId, requestDto));
    }

    // 삭제
    @DeleteMapping("/{notificationId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteNotification(@PathVariable Long notificationId) {
        notificationService.deleteNotification(notificationId);
        return ResponseEntity.noContent().build();
    }
}
