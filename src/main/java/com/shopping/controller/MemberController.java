package com.shopping.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopping.dto.MemberRegisterDto;
import com.shopping.service.MemberService;

import org.springframework.web.bind.annotation.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
@Tag(name = "맴버API", description = "/member")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid MemberRegisterDto memberRegisterDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            for (ObjectError error : bindingResult.getAllErrors()) {
                errorMessage.append(error.getDefaultMessage()).append("\n");
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage.toString());
        }
        
        memberService.register(memberRegisterDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("회원가입 성공");
    }
}
