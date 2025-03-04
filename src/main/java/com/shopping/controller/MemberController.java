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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopping.dto.Request.MemberRegisterRequestDto;
import com.shopping.dto.Request.MemberUpdateRequestDto;
import com.shopping.dto.Response.MemberInfoResponseDto;
import com.shopping.model.Member;
import com.shopping.service.MemberService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequiredArgsConstructor
@Log4j2
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


    @GetMapping("/{memberId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or #memberId == authentication.principal.id")
    public ResponseEntity<MemberInfoResponseDto> getMemberInfo(@PathVariable Long id){
        Member member = memberService.getMemberInfo(id);
        MemberInfoResponseDto responseDto = MemberInfoResponseDto.fromEntity(member);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }


    //맴버삭제
    @DeleteMapping("/{memberId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or #memberId == authentication.principal.id")
    public ResponseEntity<Void> deleteMember(@PathVariable Long id) {
        memberService.deleteMember(id);
        return ResponseEntity.noContent().build();
    }
    

    //맴버 정보 수정
    @PutMapping("/{memberId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or #memberId == authentication.principal.id")
    public ResponseEntity<Void> updateMember(@PathVariable Long memberId, @RequestBody MemberUpdateRequestDto requestDto) {
        memberService.updateMember(memberId,requestDto);
        return ResponseEntity.noContent().build();
    }
}
