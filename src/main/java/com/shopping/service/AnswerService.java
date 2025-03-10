package com.shopping.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.shopping.dto.Request.AnswerCreateRequestDto;
import com.shopping.dto.Request.AnswerUpdateRequestDto;
import com.shopping.model.Answer;
import com.shopping.model.Member;
import com.shopping.model.Question;
import com.shopping.repository.AnswerRepository;
import com.shopping.repository.MemberRepository;
import com.shopping.repository.QuestionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AnswerService {
    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;
    private final MemberRepository memberRepository;

    public Answer createAnswer(Long questionId, AnswerCreateRequestDto requestDto) {
        Question question = questionRepository.findById(questionId)
            .orElseThrow(() -> new IllegalArgumentException("해당 질문이 없습니다. id=" + questionId));
        Member member = memberRepository.findById(requestDto.getMemberId())
            .orElseThrow(() -> new IllegalArgumentException("해당 회원이 없습니다. id=" + requestDto.getMemberId()));
        Answer answer = requestDto.toEntity(member, question);
        return answerRepository.save(answer);
    }

    public List<Answer> getAnswers() {
        return answerRepository.findAll();
    }

    public Answer getAnswer(Long answerId) {
        Answer answer =answerRepository.findById(answerId)
            .orElseThrow(() -> new IllegalArgumentException("해당 답변이 없습니다. id=" + answerId));
        return answer;
    }

    public Answer updateAnswer(Long answerId, AnswerUpdateRequestDto requestDto) {
        Answer answer = answerRepository.findById(answerId)
            .orElseThrow(() -> new IllegalArgumentException("해당 답변이 없습니다. id=" + answerId));
        answer.setContent(requestDto.getContent());
        answer.setCreatedDate(LocalDateTime.now());
        return answerRepository.save(answer);
    }

    public void deleteAnswer(Long answerId) {
        Answer answer = answerRepository.findById(answerId)
            .orElseThrow(() -> new IllegalArgumentException("해당 답변이 없습니다. id=" + answerId));
        answerRepository.delete(answer);
    }
}
