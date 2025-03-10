package com.shopping.controller;

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

import com.shopping.dto.Request.AnswerCreateRequestDto;
import com.shopping.dto.Response.AnswerResponseDto;
import com.shopping.model.Answer;
import com.shopping.service.AnswerService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/answer")
@Tag(name = "답변API", description = "/answer")
@Slf4j
public class AnswerController {
    
    private final AnswerService answerService;

    //TODO : 답변생성
    @PostMapping("/{questionId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or #requestDto.memberId == authentication.principal.id")
    public ResponseEntity<AnswerResponseDto> createAnswer(@PathVariable Long questionId, @RequestBody AnswerCreateRequestDto requestDto) {
        Answer answer =answerService.createAnswer(questionId, requestDto);
        AnswerResponseDto responseDto = AnswerResponseDto.fromEntity(answer);
        return ResponseEntity.ok(responseDto);
    }

    //TODO : 전체 답변 조회
    @GetMapping
    public ResponseEntity<?> getAnswers() {
        return null;
    }

    //TODO : 답변 조회
    @GetMapping("/{id}")
    public ResponseEntity<?> getAnswer() {
        return null;
    }

    //TODO : 답변 업데이트
    @PutMapping("/{id}")
    public ResponseEntity<?> updateAnswer() {
        return null;
    }
    
    //TODO : 답변 제거
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAnswer() {
        return null;
    }
}
