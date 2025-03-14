package com.shopping.dto.Request;

import java.time.LocalDateTime;

import com.shopping.model.Member;
import com.shopping.model.Question;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
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
public class QuestionUpdateRequestDto {
    
    @NotBlank(message = "질문 제목은 필수 입력값입니다.")
    @Size(min = 1, max = 100, message = "질문 제목은 최소 1자 이상, 최대 100자 이하로 입력해야 합니다.")
    @Schema(description = "질문 제목", example = "Spring Boot에서 DTO와 Entity의 차이점은?", required = true)
    private String title;
    
    @NotBlank(message = "질문 내용은 필수 입력값입니다.")
    @Size(min = 10, max = 1000, message = "질문 내용은 최소 10자 이상, 최대 1000자 이하로 입력해야 합니다.")
    @Schema(description = "질문 내용", example = "DTO와 Entity를 어떻게 구분해서 사용하는 것이 좋을까요?", required = true)
    private String content;

    public Question toEntity(Member member) {
        return Question.builder()
            .title(this.title)
            .content(this.content)
            .member(member)
            .upDateTime(LocalDateTime.now())
            .build();
    }
}
