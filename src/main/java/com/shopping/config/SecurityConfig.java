package com.shopping.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.shopping.jwt.JwtAuthenticationFilter;
import com.shopping.jwt.JwtTokenProvider;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtTokenProvider jwtTokenProvider;


    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))                              //  CORS
            .authorizeHttpRequests(auth -> auth                                                             
                .requestMatchers("/auth/**").permitAll()                                        //  로그인,로그아웃
                .requestMatchers("/member/register").permitAll()                                //  회원가입
                .requestMatchers("/h2-console/**").permitAll()                                  //  H2 콘솔 
                .requestMatchers("/product/").permitAll()     
                .requestMatchers("/product/create").authenticated() 
                .requestMatchers("/product/update").authenticated()                                //  상품
                .requestMatchers("/images/**").permitAll()                                    //  이미지
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html" ).permitAll()//swagger
                .requestMatchers("/test/public").permitAll()                                        //테스트용
                .requestMatchers("/test/protected").authenticated()                                 //테스트용
                .anyRequest().authenticated()                                                               
            )                   
            .csrf(csrf -> csrf.disable())                                                                   //  CSRF 보호 비활성화 
            .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()))                          //  H2 Console설정
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))  //  세션 사용안함 
            .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    //  CORS 설정
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:5173")); // 프론트엔드 도메인 허용
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    //  JwtAuthenticationFilter를 Bean으로 등록
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(jwtTokenProvider);
    }

    //  비밀번호 암호화 설정(회원가입 시 비밀번호 암호화에 사용)
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //  AuthenticationManager 등록(로그인 시 인증에 사용)
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
