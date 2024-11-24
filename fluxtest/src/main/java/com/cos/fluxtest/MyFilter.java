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

	private EventNotify eventNotify;
	
	public MyFilter(EventNotify eventNotify) {
		this.eventNotify = eventNotify;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("필터 실행됨");
		
		HttpServletResponse servletResponse = (HttpServletResponse) response;
		servletResponse.setContentType("text/event-stream; charset=utf-8"); 
		// event-stream : 스트림을 열어놓고 데이터를 계속 줌. flush 할때 마다 화면에서 하나씩 순차적으로 데이타 뿌려줌.  
		
		// WebFlux : 비동기, 단일 스레드로 동작함!!!
		// Spring MVC : 사용자가 들어올때마다 개별 스레드를 만들어서 동작함.
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
		
		//  SSE 프로토콜 : 응답을 종료하지 않고 이어짐. 
		while(true) {
			try {
				if (eventNotify.getChange()) {
					int lastIndex = eventNotify.getEvents().size()-1;
					out.println("응답 : " + eventNotify.getEvents().get(lastIndex) ); 
					out.flush(); 
					eventNotify.setChange(false);
				}
				Thread.sleep(1);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
