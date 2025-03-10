package com.shopping.dto.Request;

import java.time.LocalDateTime;

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
public class QuestionCreateRequestDto {
    private String title;
    private String content;
    private Long memberId;

    public Question toEntity(Member member) {
        return Question.builder()
            .title(this.title)
            .content(this.content)
            .member(member)
            .createdDate(LocalDateTime.now())
            .build();
    }
}
