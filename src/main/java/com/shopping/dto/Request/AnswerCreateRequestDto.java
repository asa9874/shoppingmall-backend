package com.shopping.dto.Request;

import java.time.LocalDateTime;

import com.shopping.model.Answer;
import com.shopping.model.Member;
import com.shopping.model.Question;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

    @NotNull(message = "내용은 필수 입력 값입니다.")
    @Size(min = 1, max = 500, message = "내용은 1자 이상 500자 이하로 입력해야 합니다.")
    @Schema(description = "답변 내용", example = "답변내용을 작성하는곳입니다.", required = true)
    private String content;

    public Answer toEntity(Member member, Question question) {
        return Answer.builder()
                .content(this.content)
                .member(member)
                .question(question)
                .createdDate(LocalDateTime.now())
                .build();
    }
}
