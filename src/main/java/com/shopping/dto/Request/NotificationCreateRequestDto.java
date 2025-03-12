package com.shopping.dto.Request;

import java.time.LocalDateTime;

import com.shopping.model.Member;
import com.shopping.model.Notification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotificationCreateRequestDto {
    private Long receiverId;
    private Notification.NotificationType type;
    private String message;

    public Notification toEntity(Member receiver) {
        return Notification.builder()
                .receiver(receiver)
                .type(type)
                .message(message)
                .isRead(false)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
