package com.shopping.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopping.dto.Request.MemberRegisterRequestDto;
import com.shopping.dto.Response.MemberInfoResponseDto;
import com.shopping.model.Member;
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
    public ResponseEntity<String> register(@RequestBody @Valid MemberRegisterRequestDto memberRegisterDto, BindingResult bindingResult) {
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

    //이거 진짜 쓸모없이 만들었다. 나중에 지울거
    @GetMapping("/me")
    public ResponseEntity<Map<String, String>> getMe(@AuthenticationPrincipal UserDetails userDetails) {
        String nickname = memberService.getNicknameByMemberId(userDetails.getUsername()); 
        Map<String, String> response = new HashMap<>();
        response.put("nickname", nickname);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/my-info")
    public ResponseEntity<MemberInfoResponseDto> getMyInfo(){
        Member member = memberService.getMyInfo();
        MemberInfoResponseDto responseDto = MemberInfoResponseDto.fromEntity(member);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }


    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or #id == authentication.principal.id")
    public ResponseEntity<MemberInfoResponseDto> getMemberInfo(@PathVariable Long id){
        Member member = memberService.getMemberInfo(id);
        MemberInfoResponseDto responseDto = MemberInfoResponseDto.fromEntity(member);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }
}
