package com.shopping.dto.Response;

import java.time.LocalDateTime;

import com.shopping.model.Answer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AnswerResponseDto {
    private Long answerId;
    private String content;
    private Long memberId;
    private Long questionId;
    private LocalDateTime createdDate;

    public static AnswerResponseDto fromEntity(Answer answer) {
        return AnswerResponseDto.builder()
                .answerId(answer.getId())
                .content(answer.getContent())
                .memberId(answer.getMember().getId())
                .questionId(answer.getQuestion().getId())
                .createdDate(answer.getCreatedDate())
                .build();
    }
}
