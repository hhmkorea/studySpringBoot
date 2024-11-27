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
		
		/*
		 * WebFlux : 비동기, 단일 스레드로 동작함!!! 
		 * Spring MVC : 사용자가 들어올때마다 개별 스레드를 만들어서 동작함. SSE
		 * 프로토콜 : 응답을 종료하지 않고 이어짐.
		 */
		
		// 1. Reactive Streams 라이브러리를 쓰면 표준을 지켜서 응답을 할 수 있다.  -- 소비할때까지만 유지, 데이터가 있으면 바로 지속적으로 제공.
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
		
		// 2. SSE Emitter 라이브러리를 사용하면 편하게 쓸 수 있다. -- SSE 프로토콜, 소비가 끝나도 종료 안됨, 데이터를 다줘도 계속 유지하고 있음.
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

	// 3. WebFlux -> Reactive Streams 이 라이브러리가 적용된 stream을 배우고 (비동기 단일스레드 동작)
	// 4. Servlet MVC -> Reactive Streams 이 라이브러리가 적용된 stream을 배우고 (멀티 스레드 동작)
}
