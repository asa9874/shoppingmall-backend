package com.shopping.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

   
    @GetMapping("/public")
    public String publicEndpoint() {
        return "접근성공";
    }

    @GetMapping("/protected")
    public String protectedEndpoint(@AuthenticationPrincipal UserDetails userDetails) {
        return "사용자: " + userDetails.getUsername();
    }

    @GetMapping("/seller")
    public String sellertest() {
        return "접근성공";
    }

    @GetMapping("/customer")
    public String customertest() {
        return "접근성공";
    }
}
