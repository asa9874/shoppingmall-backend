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

import com.shopping.common.ApiResponse;
import com.shopping.dto.Request.NotificationCreateRequestDto;
import com.shopping.dto.Request.NotificationUpdateRequestDto;
import com.shopping.dto.Response.NotificationResponseDto;
import com.shopping.service.NotificationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/notification")
@Tag(name = "알림API", description = "/notification")
public class NotificationController {

    private final NotificationService notificationService;

    @Operation(summary = "알림 조회", description = "모든 알림을 조회합니다.")
    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<List<NotificationResponseDto>>> getNotifications() {
        List<NotificationResponseDto> responseList = notificationService.getNotifications();
        return ResponseEntity.ok(ApiResponse.success(responseList));
    }

    @Operation(summary = "알림 상세 조회", description = "특정 알림을 조회합니다.")
    @GetMapping("/{notificationId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<NotificationResponseDto>> getNotification(@PathVariable Long notificationId) {
        NotificationResponseDto responseDto = notificationService.getNotification(notificationId);
        return ResponseEntity.ok(ApiResponse.success(responseDto));
    }

    @Operation(summary = "알림 생성", description = "새로운 알림을 생성합니다.")
    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<NotificationResponseDto>> createNotification(
            @Valid @RequestBody NotificationCreateRequestDto requestDto) {
        NotificationResponseDto responseDto = notificationService.createNotification(requestDto);
        return ResponseEntity.ok(ApiResponse.success(responseDto));
    }

    @Operation(summary = "알림 업데이트", description = "특정 알림을 업데이트합니다.")
    @PutMapping("/{notificationId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<NotificationResponseDto>> updateNotification(@PathVariable Long notificationId,
            @Valid @RequestBody NotificationUpdateRequestDto requestDto) {
        NotificationResponseDto responseDto = notificationService.updateNotification(notificationId, requestDto);
        return ResponseEntity.ok(ApiResponse.success(responseDto));
    }

    @Operation(summary = "알림 삭제", description = "특정 알림을 삭제합니다.")
    @DeleteMapping("/{notificationId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteNotification(@PathVariable Long notificationId) {
        notificationService.deleteNotification(notificationId);
        return ResponseEntity.noContent().build();
    }
}
