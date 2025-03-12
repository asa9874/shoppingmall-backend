package com.shopping.controller;

import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopping.service.MemberService;

//TODO: 완성하기
@WebMvcTest(MemberController.class)  // MemberController만 테스트
public class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private MemberService memberService;

    @Autowired
    private ObjectMapper objectMapper;

}
