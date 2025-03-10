package com.shopping.controller;

import java.util.List;
import java.util.stream.Collectors;

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

import com.shopping.dto.Request.QuestionCreateRequestDto;
import com.shopping.dto.Request.QuestionDeleteRequestDto;
import com.shopping.dto.Request.QuestionUpdateRequestDto;
import com.shopping.dto.Response.AnswerResponseDto;
import com.shopping.dto.Response.QuestionResponseDto;
import com.shopping.model.Answer;
import com.shopping.model.Question;
import com.shopping.service.QuestionService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/question")
@Tag(name = "질문API", description = "/question")
@Slf4j
public class QuestionController {
    
    private final QuestionService questionService;

    //질문생성
    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') or #requestDto.memberId == authentication.principal.id")
    public ResponseEntity<QuestionResponseDto> createQuestion(@RequestBody QuestionCreateRequestDto requestDto) {
        Question question =questionService.createQuestion(requestDto);
        QuestionResponseDto responseDto = QuestionResponseDto.fromEntity(question);
        return ResponseEntity.ok(responseDto);
    }

    //질문들 조회
    @GetMapping
    public ResponseEntity<List<QuestionResponseDto>> getQuestions() {
        List<Question> questions = questionService.getQuestions();
        List<QuestionResponseDto> responseDtos = questions.stream()
                .map(QuestionResponseDto::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDtos);
    }

    //질문 조회
    @GetMapping("/{questionId}")
    public ResponseEntity<QuestionResponseDto> getQuestion(@PathVariable Long questionId) {
        Question question = questionService.getQuestion(questionId);
        QuestionResponseDto responseDto = QuestionResponseDto.fromEntity(question);
        return ResponseEntity.ok(responseDto);
    }

    //질문 업데이트
    @PutMapping("/{questionId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or #requestDto.memberId == authentication.principal.id")
    public ResponseEntity<QuestionResponseDto> updateQuestion(@RequestBody QuestionUpdateRequestDto requestDto) {
        Question question = questionService.updateQuestion(requestDto);
        QuestionResponseDto responseDto = QuestionResponseDto.fromEntity(question);
        return ResponseEntity.ok(responseDto);
    }

    //TODO : 질문 제거
    @DeleteMapping("/{questionId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or #requestDto.memberId == authentication.principal.id")
    public ResponseEntity<Void> deleteQuestion(@RequestBody QuestionDeleteRequestDto requestDto,@PathVariable Long questionId) {
        questionService.deleteQuestion(questionId,requestDto);
        return ResponseEntity.noContent().build();
    }

    //질문에 답변조회
    @GetMapping("/{questionId}/answer")
    public ResponseEntity<List<AnswerResponseDto>> getAnswers(@PathVariable Long questionId) {
        List<Answer> answers =questionService.getAnswers(questionId);
        List<AnswerResponseDto> responseDto = answers.stream()
                .map(AnswerResponseDto::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDto);
    }
    
}
