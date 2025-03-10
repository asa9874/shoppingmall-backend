package com.shopping.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/question")
@Tag(name = "질문API", description = "/question")
@Slf4j
public class QuestionController {
    
    //TODO : 질문생성
    @PostMapping
    public ResponseEntity<?> createQuestion() {
        return null;
    }

    //TODO : 질문들 조회
    @GetMapping
    public ResponseEntity<?> getQuestions() {
        return null;
    }

    //TODO : 질문 조회
    @GetMapping("/{id}")
    public ResponseEntity<?> getQuestion() {
        return null;
    }

    //TODO : 질문 업데이트
    @PutMapping("/{id}")
    public ResponseEntity<?> updateQuestion() {
        return null;
    }

    //TODO : 질문 제거
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteQuestion() {
        return null;
    }

    //TODO : 질문에 답변조회
    @GetMapping("/{id}/answer")
    public ResponseEntity<?> getAnswers() {
        return null;
    }
    
}
