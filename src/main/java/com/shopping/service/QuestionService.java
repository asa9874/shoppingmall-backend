package com.shopping.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.shopping.dto.Request.QuestionCreateRequestDto;
import com.shopping.model.Member;
import com.shopping.model.Question;
import com.shopping.repository.AnswerRepository;
import com.shopping.repository.MemberRepository;
import com.shopping.repository.QuestionRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class QuestionService {

    private final MemberRepository memberRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    public Question createQuestion(QuestionCreateRequestDto requestDto) {
        Member member = memberRepository.findById(requestDto.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다."));

        Question question = Question.builder()
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .member(member)
                .createdDate(LocalDateTime.now())
                .build();

        return questionRepository.save(question);
    }
}
