package com.shopping.dto.Request;

import java.time.LocalDateTime;

import com.shopping.model.Answer;
import com.shopping.model.Member;
import com.shopping.model.Question;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnswerCreateRequestDto {
    private String content;
    private Long memberId;

    public Answer toEntity(Member member,Question question) {
        return Answer.builder()
            .content(this.content)
            .member(member)
            .question(question)
            .createdDate(LocalDateTime.now())
            .build();
    }
}
