package com.cos.blog.config;

/**
 * packageName    : com.cos.blog.config
 * fileName       : SecurityConfig
 * author         : dotdot
 * date           : 2024-04-13
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-04-13        dotdot       최초 생성
 */

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

// 빈 등록 : 스프링 컨테이너에서 객체를 관리할 수 있게 하는 것
@Configuration // 빈등록 (IoC관리)
public class SecurityConfig {

    @Bean
    BCryptPasswordEncoder encode() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth ->
                        auth.requestMatchers("/auth/**", "/WEB-INF/**").permitAll()
                            .anyRequest().authenticated());
        http.formLogin(f -> f.loginPage("/auth/loginForm")
                        .permitAll()
        );

        return http.build();
    }

}
