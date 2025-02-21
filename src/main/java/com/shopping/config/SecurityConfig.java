package com.shopping.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            //  인증 및 권한 설정
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/**").permitAll() // 다허용
                .anyRequest().authenticated() // 나머지 모든 요청은 인증 필요
            )

            //  CSRF 보호 (JWT 사용 시 비활성화)
            .csrf(csrf -> csrf.disable())

            //  H2 Console을 위한 헤더 설정
            .headers(headers -> headers
                .frameOptions(frame -> frame.sameOrigin()))

            //  로그인 설정
            .formLogin(formLogin -> formLogin
                .loginPage("/auth/login") // 로그인 페이지 지정
                .defaultSuccessUrl("/") // 로그인 성공 후 리디렉션
                .permitAll())

            //  로그아웃 설정
            .logout(logout -> logout
                .logoutUrl("/auth/logout")
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll());

        return http.build();
    }

    //  비밀번호 암호화 설정
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //  AuthenticationManager 등록
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
