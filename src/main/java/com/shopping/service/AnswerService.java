package com.shopping.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import com.shopping.dto.Request.AnswerCreateRequestDto;
import com.shopping.dto.Request.AnswerUpdateRequestDto;
import com.shopping.dto.Response.AnswerResponseDto;
import com.shopping.model.Answer;
import com.shopping.model.Member;
import com.shopping.model.Question;
import com.shopping.repository.AnswerRepository;
import com.shopping.repository.MemberRepository;
import com.shopping.repository.QuestionRepository;
import com.shopping.util.SecurityUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AnswerService {
    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;
    private final MemberRepository memberRepository;

    public AnswerResponseDto createAnswer(Long questionId, AnswerCreateRequestDto requestDto, Long memberId) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new IllegalArgumentException("해당 질문이 없습니다. id=" + questionId));
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 없습니다. id=" + memberId));
        Answer answer = requestDto.toEntity(member, question);
        answerRepository.save(answer);
        return AnswerResponseDto.fromEntity(answer);
    }

    public List<AnswerResponseDto> getAnswers() {
        List<Answer> answers = answerRepository.findAll();
        List<AnswerResponseDto> responseDto = answers.stream().map(AnswerResponseDto::fromEntity).toList();
        return responseDto;
    }

    public AnswerResponseDto getAnswer(Long answerId) {
        Answer answer = answerRepository.findById(answerId)
                .orElseThrow(() -> new IllegalArgumentException("해당 답변이 없습니다. id=" + answerId));
        AnswerResponseDto responseDto = AnswerResponseDto.fromEntity(answer);
        return responseDto;
    }

    public AnswerResponseDto updateAnswer(Long answerId, AnswerUpdateRequestDto requestDto, Long memberId) {
        Answer answer = answerRepository.findById(answerId)
                .orElseThrow(() -> new IllegalArgumentException("해당 답변이 없습니다. id=" + answerId));

        if (!SecurityUtil.isAdminOrOwner(answer.getMember().getId(), memberId)) {
            throw new AccessDeniedException("해당 답변을 삭제할 권한이 없습니다.");
        }

        answer.setContent(requestDto.getContent());
        answer.setCreatedDate(LocalDateTime.now());
        answerRepository.save(answer);
        return AnswerResponseDto.fromEntity(answer);
    }

    public void deleteAnswer(Long answerId, Long memberId) {
        Answer answer = answerRepository.findById(answerId)
                .orElseThrow(() -> new IllegalArgumentException("해당 답변이 없습니다. id=" + answerId));

        if (!SecurityUtil.isAdminOrOwner(answer.getMember().getId(), memberId)) {
            throw new AccessDeniedException("해당 답변을 삭제할 권한이 없습니다.");
        }
        answerRepository.delete(answer);
    }

}
