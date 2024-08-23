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
public class MyFilter1 implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("필터1");
        chain.doFilter(request, response); // 계속 프로세스 진행을 위해 chain에 넘겨줘야함.
    }
}

