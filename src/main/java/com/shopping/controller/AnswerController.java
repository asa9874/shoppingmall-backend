package com.shopping.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopping.common.ApiResponse;
import com.shopping.dto.Request.AnswerCreateRequestDto;
import com.shopping.dto.Request.AnswerUpdateRequestDto;
import com.shopping.dto.Response.AnswerResponseDto;
import com.shopping.jwt.CustomUserDetails;
import com.shopping.service.AnswerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
    public ResponseEntity<ApiResponse<AnswerResponseDto>> createAnswer(
            @PathVariable Long questionId,
            @Valid @RequestBody AnswerCreateRequestDto requestDto,
            @AuthenticationPrincipal CustomUserDetails member) {
        AnswerResponseDto responseDto = answerService.createAnswer(questionId, requestDto, member.getId());
        return ResponseEntity.ok(ApiResponse.success(responseDto));
    }

    @Operation(summary = "답변 업데이트", description = "특정 답변을 업데이트합니다.(본인 권한)")
    @PutMapping("/{answerId}")
    public ResponseEntity<ApiResponse<AnswerResponseDto>> updateAnswer(
            @PathVariable Long answerId,
            @Valid @RequestBody AnswerUpdateRequestDto requestDto,
            @AuthenticationPrincipal CustomUserDetails member) {
        AnswerResponseDto responseDto = answerService.updateAnswer(answerId, requestDto, member.getId());
        return ResponseEntity.ok(ApiResponse.success(responseDto));
    }

    @Operation(summary = "답변 제거", description = "특정 답변을 제거합니다.(본인 권한)")
    @DeleteMapping("/{answerId}")
    public ResponseEntity<Void> deleteAnswer(
            @PathVariable Long answerId,
            @AuthenticationPrincipal CustomUserDetails member) {
        answerService.deleteAnswer(answerId, member.getId());
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "전체 답변 조회", description = "모든 답변을 조회합니다.(Admin 권한)")
    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<List<AnswerResponseDto>>> getAnswers() {
        List<AnswerResponseDto> responseDto = answerService.getAnswers();
        return ResponseEntity.ok(ApiResponse.success(responseDto));
    }

    @Operation(summary = "답변 조회", description = "특정 답변을 조회합니다.(Admin 권한)")
    @GetMapping("/{answerId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<AnswerResponseDto>> getAnswer(@PathVariable Long answerId) {
        AnswerResponseDto responseDto = answerService.getAnswer(answerId);
        return ResponseEntity.ok(ApiResponse.success(responseDto));
    }
}
