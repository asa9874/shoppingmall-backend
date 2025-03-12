package com.shopping.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.shopping.dto.Request.NotificationCreateRequestDto;
import com.shopping.dto.Request.NotificationUpdateRequestDto;
import com.shopping.dto.Response.NotificationResponseDto;
import com.shopping.model.Member;
import com.shopping.model.Notification;
import com.shopping.repository.MemberRepository;
import com.shopping.repository.NotificationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final MemberRepository memberRepository;

    //전체조회
    public List<NotificationResponseDto> getNotifications() {
        List<Notification> notifications = notificationRepository.findAll();
        List<NotificationResponseDto> responseDto = notifications.stream()
                .map(NotificationResponseDto::fromEntity)
                .toList(); 
        return responseDto;
    }

    //상세조회
    public NotificationResponseDto getNotification(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new IllegalArgumentException("해당 알림이 없습니다."));
        return NotificationResponseDto.fromEntity(notification);
    }

    //생성
    public NotificationResponseDto createNotification(NotificationCreateRequestDto requestDto) {
        Member receiver = memberRepository.findById(requestDto.getReceiverId())
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 없습니다."));
        Notification notification = requestDto.toEntity(receiver);
        notificationRepository.save(notification);
        return NotificationResponseDto.fromEntity(notification);
    }

    //업데이트
    public NotificationResponseDto updateNotification(Long notificationId, NotificationUpdateRequestDto requestDto) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new IllegalArgumentException("해당 알림이 없습니다."));
        notification.setMessage(requestDto.getMessage());
        notification.setRead(requestDto.isRead());
        notification.setType(requestDto.getType());
        notificationRepository.save(notification);

        return NotificationResponseDto.fromEntity(notification);
    }

    //삭제
    public void deleteNotification(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new IllegalArgumentException("해당 알림이 없습니다."));
        notificationRepository.delete(notification);
    }
}
