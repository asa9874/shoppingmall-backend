package com.shopping.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopping.service.TestService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {
    private final TestService testService;
   
    @GetMapping("/public")
    public String publicEndpoint() {
        return "접근성공";
    }

    @GetMapping("/protected")
    public String protectedEndpoint(@AuthenticationPrincipal UserDetails userDetails) {
        return "사용자: " + userDetails.getUsername();
    }

    @GetMapping("/admin")
    
    public String adminEndpoint(@AuthenticationPrincipal UserDetails userDetails) {
        return "어드민: " + userDetails.getUsername();
    }

    @GetMapping("/seller")
    public String sellertest() {
        return "접근성공";
    }

    @GetMapping("/customer")
    public String customertest() {
        return "접근성공";
    }

    @GetMapping("/redistest")
    public String redistest() {
        return testService.testRedisConnection();
    }
}
