package com.cos.jwt.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * packageName    : com.cos.jwt.filter
 * fileName       : MyFilter1
 * author         : dotdot
 * date           : 2024-08-22
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-08-22        dotdot       최초 생성
 */
public class MyFilter3 implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        /* 토큰 : cos 이걸 만들어줘야 함.
         * id,pw 정상적으로 들어와서 로그인 완료되면 토큰 생성, 그걸 응답해줌.
         * 요청할 때 마다 header에 Authorization에 value값으로 토큰 가져옴.
         * 그 때 토큰 넘어오면 이 토큰이 내가 만든 토큰이 맞는지 검증만 하면 됨. ( RSA, HS256 )
        */
        if(req.getMethod().equals("POST")) {
            System.out.println("POST 요청됨");
            String headerAuth = req.getHeader("Authorization");
            System.out.println(headerAuth);
            System.out.println("필터3");

            if (headerAuth.equals("cos")) {
                chain.doFilter(req,res); // 계속 프로세스 진행을 위해 chain에 넘겨줘야함.
            } else {
                PrintWriter outPrintWriter = res.getWriter();
                outPrintWriter.println("인증안됨");
            }
        }
    }
}
