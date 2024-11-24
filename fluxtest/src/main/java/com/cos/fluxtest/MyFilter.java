package com.cos.fluxtest;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletResponse;

public class MyFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("필터 실행됨");
		
		HttpServletResponse servletResponse = (HttpServletResponse) response;
		servletResponse.setContentType("text/event-stream; charset=utf-8"); // event-stream : 스트림을 열어놓고 데이터를 계속 줌. flush 할때 마다 화면에서 하나씩 순차적으로 데이타 뿌려줌. 
		PrintWriter out = servletResponse.getWriter();
		for (int i = 0; i < 5; i++) {
			out.println("응답 : " + i ); // 버퍼에 데이타 쌓기.
			out.flush(); // 버퍼를 비우다. 
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}

}
