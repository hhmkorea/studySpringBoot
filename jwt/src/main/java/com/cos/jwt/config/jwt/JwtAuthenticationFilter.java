package com.cos.jwt.config.jwt;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * packageName    : com.cos.jwt.config.jwt
 * fileName       : JwtAuthenticationFilter
 * author         : dotdot
 * date           : 2024-08-23
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-08-23        dotdot       최초 생성
 */

// 스프링 시큐리티에서 UsernamePasswordAuthenticationFilter 가 있음.
// /login 요청해서 usename, password 전송하면(post)
// UsernamePasswordAuthenticationFilter가 동작을 함.
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        System.out.println("JwtAuthenticationFilter : 로그인 시도중");

        // 1. username, password 받아서
        try {
            BufferedReader br = request.getReader();

            String input = null;
            while ((input = br.readLine()) !=null) {
                System.out.println(input);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("=======================");

        // 2. 정상인지 로그인 시도 해보기. authenticationManager로 로그인 시도!!
        // PrincipalDetailsService 호출 됨. loadUserByUsername() 함수 실행됨.

        // 3. PrincipalDetails를 세션에 담고(권한 관리를 위해서)

        // 4. JWT 토큰을 만들어서 응답해주면 됨. 
        
        return super.attemptAuthentication(request, response);
    }
}
