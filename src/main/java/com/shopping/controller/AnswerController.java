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

import com.shopping.dto.Request.AnswerCreateRequestDto;
import com.shopping.dto.Request.AnswerDeleteRequestDto;
import com.shopping.dto.Request.AnswerUpdateRequestDto;
import com.shopping.dto.Response.AnswerResponseDto;
import com.shopping.model.Answer;
import com.shopping.service.AnswerService;

import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "답변 생성", description = "질문에 대한 답변을 생성합니다.")
    @PostMapping("/{questionId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or #requestDto.memberId == authentication.principal.id")
    public ResponseEntity<AnswerResponseDto> createAnswer(@PathVariable Long questionId, @RequestBody AnswerCreateRequestDto requestDto) {
        Answer answer = answerService.createAnswer(questionId, requestDto);
        AnswerResponseDto responseDto = AnswerResponseDto.fromEntity(answer);
        return ResponseEntity.ok(responseDto);
    }

    @Operation(summary = "전체 답변 조회", description = "모든 답변을 조회합니다.")
    @GetMapping
    public ResponseEntity<List<AnswerResponseDto>> getAnswers() {
        List<Answer> answers = answerService.getAnswers();
        List<AnswerResponseDto> responseDto = answers.stream().map(AnswerResponseDto::fromEntity).toList();
        return ResponseEntity.ok(responseDto);
    }

    @Operation(summary = "답변 조회", description = "특정 답변을 조회합니다.")
    @GetMapping("/{answerId}")
    public ResponseEntity<AnswerResponseDto> getAnswer(@PathVariable Long answerId) {
        Answer answer = answerService.getAnswer(answerId);
        AnswerResponseDto responseDto = AnswerResponseDto.fromEntity(answer);
        return ResponseEntity.ok(responseDto);
    }

    @Operation(summary = "답변 업데이트", description = "특정 답변을 업데이트합니다.")
    @PutMapping("/{answerId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or #requestDto.memberId == authentication.principal.id")
    public ResponseEntity<AnswerResponseDto> updateAnswer(@PathVariable Long answerId, @RequestBody AnswerUpdateRequestDto requestDto) {
        Answer answer = answerService.updateAnswer(answerId, requestDto);
        AnswerResponseDto responseDto = AnswerResponseDto.fromEntity(answer);
        return ResponseEntity.ok(responseDto);
    }
    
    @Operation(summary = "답변 제거", description = "특정 답변을 제거합니다.")
    @DeleteMapping("/{answerId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or #requestDto.memberId == authentication.principal.id")
    public ResponseEntity<Void> deleteAnswer(@PathVariable Long answerId, @RequestBody AnswerDeleteRequestDto requestDto) {
        answerService.deleteAnswer(answerId);
        return ResponseEntity.noContent().build();
    }
}
