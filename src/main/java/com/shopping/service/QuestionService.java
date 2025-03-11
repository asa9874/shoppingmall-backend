package com.shopping.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.shopping.dto.Request.QuestionCreateRequestDto;
import com.shopping.dto.Request.QuestionDeleteRequestDto;
import com.shopping.dto.Request.QuestionUpdateRequestDto;
import com.shopping.model.Answer;
import com.shopping.model.Member;
import com.shopping.model.Question;
import com.shopping.repository.MemberRepository;
import com.shopping.repository.QuestionRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class QuestionService {

    private final MemberRepository memberRepository;
    private final QuestionRepository questionRepository;

    public Question createQuestion(QuestionCreateRequestDto requestDto) {
        Member member = memberRepository.findById(requestDto.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다."));
        Question question = requestDto.toEntity(member);
        return questionRepository.save(question);
    }

    public List<Question> getQuestions() {
        List<Question> questions = questionRepository.findAll();
        return questions;
    }

    public Question getQuestion(Long id){
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 질문이 존재하지 않습니다."));
        return question;
    }

    public Question updateQuestion(QuestionUpdateRequestDto requestDto){
        Question question = questionRepository.findById(requestDto.getQuestionId())
                .orElseThrow(() -> new IllegalArgumentException("해당 질문이 존재하지 않습니다."));
        question.setContent(requestDto.getContent());
        question.setTitle(requestDto.getTitle());
        question.setUpDateTime(LocalDateTime.now());
        return questionRepository.save(question);
    }

    @Transactional
    public void deleteQuestion(Long questionId,QuestionDeleteRequestDto requestDto){
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new IllegalArgumentException("해당 질문이 존재하지 않습니다."));
        questionRepository.delete(question);
    }

    public List<Answer> getAnswers(Long questionId){
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new IllegalArgumentException("해당 질문이 존재하지 않습니다."));
        return question.getAnswers();
    }

}
