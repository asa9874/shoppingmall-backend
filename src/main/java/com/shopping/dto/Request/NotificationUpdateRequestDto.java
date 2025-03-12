package com.shopping.dto.Request;

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
public class NotificationUpdateRequestDto {
    private Long receiverId;
    private Notification.NotificationType type;
    private String message;
    private boolean isRead;
}
