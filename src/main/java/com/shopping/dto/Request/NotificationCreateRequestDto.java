package com.shopping.dto.Request;

import java.time.LocalDateTime;

import com.shopping.model.Member;
import com.shopping.model.Notification;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

    @NotNull(message = "받는 사람 ID는 필수입니다.")
    @Schema(description = "받는 사람 ID", example = "123", required = true)
    private Long receiverId;

    @NotNull(message = "알림 유형은 필수입니다.")
    @Schema(description = "알림 유형", example = "ORDER_CONFIRMATION", required = true)
    private Notification.NotificationType type;

    @NotNull(message = "메시지는 필수입니다.")
    @Size(min = 1,max = 500, message = "메시지는 1자 ~ 500자 사이이어야 합니다.")
    @Schema(description = "알림 메시지", example = "새로운 주문이 생성되었습니다.", required = true)
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