package com.cos.security1.config;

// 1. 코드받기(인증), 2. 엑세스토큰(권한), 3. 사용자프로필 정보를 가져오고,
// 4-1. 그 정보를 토대로 회원가입을 자동으로 진행시키기도 함.
// 4-2. (이메일, 전화번호, 이름, 아이디) 쇼핑몰 -> (집주소), 백화점몰 -> (VIP등급, 일반등급)

import com.cos.security1.config.oauth.PrincipalOauth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
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

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true) // 특정 주소 접근시 권한 및 인증을 위한 어노테이션 활성화.
public class SecurityConfig {

    @Autowired
    private PrincipalOauth2UserService principalOauth2UserService;

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
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/user/**").authenticated() // 인증만되면 들어갈 수 있는 주소.
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().permitAll()
        );

        // 3. 로그인 처리 프로세스 설정
        http.formLogin(f -> f.loginPage("/loginForm")
                .loginProcessingUrl("/login") // login 주소가 호출이 되면 시큐리티가 낚아채서 대신 로그인을 진행해줌.
                .defaultSuccessUrl("/") // 로그인하면 main페이지로 이동.
                //.usernameParameter("바꿀username") // 로그인 username을 다른 명명으로 바꾸고 싶을때 사용.
        );

        // 4.oauth2 설정
        http.oauth2Login(oauth -> // OAuth2 로그인 성공 이후 사용자 정보를 가져올 때의 설정을 담당
                            oauth.loginPage("/loginForm")   // 구글 로그인이 완료된 뒤의 후처리가 필요함. Tip. 코드X, (엑세스토큰+사용자프로필정보 O)
                                .userInfoEndpoint(c -> c.userService(principalOauth2UserService))
                                //.successHandler(null) // 로그인 성공 시 핸들러
        );

        return http.build();
    }
}
