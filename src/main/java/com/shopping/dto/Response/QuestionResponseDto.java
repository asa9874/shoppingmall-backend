package com.shopping.dto.Response;

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
public class QuestionResponseDto {
    private String title;
    private String content;
    private Long memberId;
    private Long questionId;
    private String createdDate;
    private String memberName;
    private String upDateTime;

    public static QuestionResponseDto fromEntity(Question question) {
        return QuestionResponseDto.builder()
                .title(question.getTitle())
                .content(question.getContent())
                .memberId(question.getMember().getId())
                .questionId(question.getId())
                .createdDate(question.getCreatedDate().toString())
                .memberName(question.getMember().getNickname())
                .upDateTime(question.getUpDateTime() != null ? question.getUpDateTime().toString() : "")
                .build();
    }
}
