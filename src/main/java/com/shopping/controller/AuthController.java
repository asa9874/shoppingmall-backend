package com.shopping.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Tag(name = "인증API", description = "/auth")
public class AuthController {
    
    @PostMapping("/login")
    public void login(){
        
    }

    
    @PostMapping("/logout")
    public void logout(){
        
    }
}
