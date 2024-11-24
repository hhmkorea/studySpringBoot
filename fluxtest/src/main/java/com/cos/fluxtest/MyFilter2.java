package com.cos.fluxtest;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletResponse;

public class MyFilter2 implements Filter {

	private EventNotify eventNotify;
	
	public MyFilter2(EventNotify eventNotify) {
		this.eventNotify = eventNotify;
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("필터2 실행됨");
		// 데이터를 하나 발생시켜서 
		eventNotify.add("새로운 데이터");
	}

}
