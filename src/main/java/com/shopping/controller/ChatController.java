package com.shopping.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.shopping.dto.ChatMessage;

@Controller
public class ChatController {

    @MessageMapping("/chat.sendMessage")  // 클라이언트가 "/app/chat.sendMessage"로 보낸 메시지를 처리
    @SendTo("/topic/public")  // 구독한 클라이언트들에게 전달
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
        return chatMessage;  // 그대로 브로드캐스트
    }
}