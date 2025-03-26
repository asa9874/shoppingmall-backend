package com.shopping.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopping.common.ApiResponse;
import com.shopping.dto.Response.MemberInfoResponseDto;
import com.shopping.service.MemberService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
@Tag(name = "관리자API", description = "/admin")
@Log4j2
public class AdminControlloer {
    private final MemberService memberService;

    @GetMapping("/test")
    public ResponseEntity<ApiResponse<String>> test() {
        return ResponseEntity.ok(ApiResponse.success("test"));
    }

    //TODO: 유저들 정보 조회
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/users")
    public ResponseEntity<ApiResponse<List<MemberInfoResponseDto>>> getUsers() {
        List<MemberInfoResponseDto> memberInfoResponseDtoList = memberService.getMembers();
        return ResponseEntity.ok(ApiResponse.success(memberInfoResponseDtoList));
    }


    //TODO: 유저 밴하기
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/ban/member/{memberId}")  
    public ResponseEntity<ApiResponse<String>> banUser() {

        return ResponseEntity.ok(ApiResponse.success("banUser"));
    }


    //TODO: 유저 밴해제
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/unban/member/{memberId}")
    public ResponseEntity<ApiResponse<String>> unBanUser() {
        
        return ResponseEntity.ok(ApiResponse.success("unBanUser"));
    }

    
}
