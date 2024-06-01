package com.cos.security1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * packageName    : com.cos.security1.config
 * fileName       : SecurityConfig
 * author         : dotdot
 * date           : 2024-06-01
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-06-01        dotdot       최초 생성
 */

@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true) // 특정 주소 접근시 권한 및 인증을 위한 어노테이션 활성화.
public class SecurityConfig {
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder encodePwd() {
        return new BCryptPasswordEncoder();
    }

    // 최신 버전(2.7)으로 시큐리티 필터 변경
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // 1. csrf 비활성화
        http.csrf(c -> c.disable());

        // 2. 인증 주소 설정 (WEB-INF/** 추가해줘야 함. 아니면 인증이 필요한 주소로 무한 리다이렉션 일어남)
        http.authorizeHttpRequests(a -> a
                .requestMatchers("/user/**").authenticated()
                //.requestMatchers("/admin/**").hasRole("ROLE_ADMIN")
                .anyRequest().permitAll()
        );

        // 3. 로그인 처리 프로세스 설정
        http.formLogin(f -> f.loginPage("/loginForm")
                .loginProcessingUrl("/loginProc")
                .defaultSuccessUrl("/")
        );

        return http.build();
    }
}
