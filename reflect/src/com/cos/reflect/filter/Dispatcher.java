package com.cos.reflect.filter;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cos.reflect.anno.RequestMapping;
import com.cos.reflect.controller.UserController;

// 분기 시키기 - 라우터 역할
public class Dispatcher implements Filter {

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		
//		System.out.println("컨택스트 패스 : " + request.getContextPath()); // 프로젝트 시작 주소
//		System.out.println("식별자 주소 : " + request.getRequestURI()); // 프로젝트 끝 주소
//		System.out.println("전체 주소 : " + request.getRequestURL()); // 프로젝트 전체 주소
		
		// /user 남기기
		String endPoint = request.getRequestURI().replaceAll(request.getContextPath(), ""); 
		System.out.println("엔드포인트 : " + endPoint);
		
		UserController userController = new UserController();
// 1. 리플렉션 적용 전 ----------------
//		if (endPoint.equals("/join")) {
//			userController.join();
//		} else if (endPoint.equals("/login")) {
//			userController.login();
//		} else if (endPoint.equals("/user")) {
//			userController.user();
//		}
		
		// 리플렉션 -> 메서드를 런타임 시점에서 찾아내서 실행, 
		Method[] methods = userController.getClass().getDeclaredMethods(); // 그 파일의 메서드만!! 
// 2. 리플렉션 적용 후 ----------------
//		for (Method method : methods) {
//			//System.out.println(method.getName());
//			if (endPoint.equals("/" + method.getName())) {
//				try {
//					method.invoke(userController);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		}
		
		for (Method method:methods) { // 4바퀴 (join, login, user, hello)
			Annotation annotation = method.getDeclaredAnnotation(RequestMapping.class); 
			RequestMapping requestMapping = (RequestMapping) annotation; // 다운캐스팅한 이유? 
			System.out.println(requestMapping.value()); // 주소를 annotation에 걸어서 함수 호출 가능.
			
			if (requestMapping.value().equals(endPoint)) {
				try {
					String path = (String)method.invoke(userController);
					
					RequestDispatcher dis = request.getRequestDispatcher(path); // 내부에서 실행하기 때문에 필터를 안탐. 외부에서는 필터 들어와서 못탐.
					dis.forward(request, response);
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			}
		}
	}

}
