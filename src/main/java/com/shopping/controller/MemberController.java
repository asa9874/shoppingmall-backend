package com.shopping.controller;

import java.util.HashMap;
import java.util.List;
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
import com.shopping.dto.Response.NotificationResponseDto;
import com.shopping.jwt.CustomUserDetails;
import com.shopping.model.Member;
import com.shopping.service.MemberService;

import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "회원가입", description = "사용자를 회원가입합니다.")
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

    @Operation(summary = "내 정보 상세 조회", description = "현재 로그인한 사용자의 상세 정보를 조회합니다.")
    @GetMapping("/my-info")
    public ResponseEntity<MemberInfoResponseDto> getMyInfo(@AuthenticationPrincipal CustomUserDetails member){
        MemberInfoResponseDto responseDto = memberService.getMyInfo(member.getId());
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @Operation(summary = "회원 정보 조회", description = "특정 회원의 정보를 조회합니다.")
    @GetMapping("/{memberId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or #memberId == authentication.principal.id")
    public ResponseEntity<MemberInfoResponseDto> getMemberInfo(@PathVariable Long memberId){
        MemberInfoResponseDto responseDto = memberService.getMemberInfo(memberId);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @Operation(summary = "회원 삭제", description = "특정 회원을 삭제합니다.")
    @DeleteMapping("/{memberId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or #memberId == authentication.principal.id")
    public ResponseEntity<Void> deleteMember(@PathVariable Long memberId) {
        memberService.deleteMember(memberId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "회원 정보 수정", description = "특정 회원의 정보를 수정합니다.")
    @PutMapping("/{memberId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or #memberId == authentication.principal.id")
    public ResponseEntity<Void> updateMember(@PathVariable Long memberId, @RequestBody MemberUpdateRequestDto requestDto) {
        memberService.updateMember(memberId, requestDto);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "알림 조회", description = "특정 회원의 알림을 조회합니다.")
    @GetMapping("/{memberId}/notification")
    @PreAuthorize("hasRole('ROLE_ADMIN') or #memberId == authentication.principal.id")
    public ResponseEntity<List<NotificationResponseDto>> getNotification(@PathVariable Long memberId) {
        List<NotificationResponseDto> notification = memberService.getNotification(memberId);
        return ResponseEntity.ok(notification);
    }

    @Operation(summary = "알림 상세 조회", description = "특정 회원의 알림 상세 정보를 조회합니다.")
    @GetMapping("/{memberId}/notification/{notificationId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or #memberId == authentication.principal.id")
    public ResponseEntity<NotificationResponseDto> getNotificationDetail(@PathVariable Long memberId, @PathVariable Long notificationId) {
        NotificationResponseDto notification = memberService.getNotificationDetail(notificationId);
        return ResponseEntity.ok(notification);
    }
}
