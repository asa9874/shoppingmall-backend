package com.shopping.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopping.dto.Request.MemberRegisterRequestDto;
import com.shopping.service.MemberService;

@WebMvcTest(MemberController.class) // MemberController만 테스트
@AutoConfigureMockMvc
public class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MemberService memberService;

    @Autowired
    private ObjectMapper objectMapper;

    // 회원가입 테스트
    @Test
    @WithMockUser
    void register_ShouldReturnCreatedStatus() throws Exception {
        // given: Mock 데이터 설정
        MemberRegisterRequestDto registerRequestDto = new MemberRegisterRequestDto("user1234", "password123!",
                "user123_nickname", "CUSTOMER");

        // when & then: HTTP POST 요청을 보내고 응답 검증
        mockMvc.perform(post("/member/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerRequestDto)))
                .andExpect(status().isCreated()) 
                .andExpect(content().string("회원가입 성공"));
    }
}
