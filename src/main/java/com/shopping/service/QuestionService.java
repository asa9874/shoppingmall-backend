package com.shopping.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import com.shopping.dto.Request.QuestionCreateRequestDto;
import com.shopping.dto.Request.QuestionUpdateRequestDto;
import com.shopping.dto.Response.AnswerResponseDto;
import com.shopping.dto.Response.QuestionResponseDto;
import com.shopping.model.Answer;
import com.shopping.model.Member;
import com.shopping.model.Question;
import com.shopping.repository.MemberRepository;
import com.shopping.repository.QuestionRepository;
import com.shopping.util.SecurityUtil;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class QuestionService {

    private final MemberRepository memberRepository;
    private final QuestionRepository questionRepository;

    public QuestionResponseDto createQuestion(QuestionCreateRequestDto requestDto, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다."));
        Question question = requestDto.toEntity(member);
        questionRepository.save(question);
        QuestionResponseDto responseDto = QuestionResponseDto.fromEntity(question);
        return responseDto;
    }

    public List<QuestionResponseDto> getQuestions() {
        List<Question> questions = questionRepository.findAll();
        List<QuestionResponseDto> responseDtos = questions.stream()
                .map(QuestionResponseDto::fromEntity)
                .collect(Collectors.toList());
        return responseDtos;
    }

    public QuestionResponseDto getQuestion(Long id) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 질문이 존재하지 않습니다."));
        QuestionResponseDto responseDto = QuestionResponseDto.fromEntity(question);
        return responseDto;
    }

    public QuestionResponseDto updateQuestion(QuestionUpdateRequestDto requestDto, Long memberId, Long questionId) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new IllegalArgumentException("해당 질문이 존재하지 않습니다."));

        if (!SecurityUtil.isAdminOrOwner(question.getMember().getId(), memberId)) {
            throw new AccessDeniedException("해당 질문을 업데이트할 권한이 없습니다.");
        }

        question.setContent(requestDto.getContent());
        question.setTitle(requestDto.getTitle());
        question.setUpDateTime(LocalDateTime.now());
        questionRepository.save(question);
        QuestionResponseDto responseDto = QuestionResponseDto.fromEntity(question);
        return responseDto;
    }

    @Transactional
    public void deleteQuestion(Long questionId, Long memberId) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new IllegalArgumentException("해당 질문이 존재하지 않습니다."));
        if (!SecurityUtil.isAdminOrOwner(question.getMember().getId(), memberId)) {
            throw new AccessDeniedException("해당 질문을 삭제할 권한이 없습니다.");
        }

        questionRepository.delete(question);
    }

    public List<AnswerResponseDto> getAnswers(Long questionId) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new IllegalArgumentException("해당 질문이 존재하지 않습니다."));

        List<Answer> answers = question.getAnswers();
        List<AnswerResponseDto> responseDto = answers.stream()
                .map(AnswerResponseDto::fromEntity)
                .collect(Collectors.toList());
        return responseDto;
    }

}
