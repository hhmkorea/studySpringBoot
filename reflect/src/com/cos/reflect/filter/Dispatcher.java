package com.cos.reflect.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.net.http.HttpRequest;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.valves.rewrite.InternalRewriteMap.UpperCase;

import com.cos.reflect.anno.RequestMapping;
import com.cos.reflect.controller.UserController;
import com.cos.reflect.controller.dto.LoginDto;

// 분기 시키기 - 라우터 역할
public class Dispatcher implements Filter {

	private boolean isMatching = false;
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		
		String endPoint = request.getRequestURI().replaceAll(request.getContextPath(), ""); 
		System.out.println("엔드포인트 : " + endPoint); // /user/login 
		
		UserController userController = new UserController();
		
		Method[] methods = userController.getClass().getDeclaredMethods(); // 그 파일에 메서드만!!
		
		for (Method method:methods) {
			Annotation annotation = method.getDeclaredAnnotation(RequestMapping.class); 
			RequestMapping requestMapping = (RequestMapping) annotation; // 다운캐스팅하는 이유? .value 함수 호출하려고!!
			
			if (requestMapping.value().equals(endPoint)) {
				isMatching = true;
				try {
					Parameter[] params = method.getParameters(); // login(LoginDto dto)
					String path = null;
					if (params.length != 0) {
						// 해당 오브젝트(dtoInstance)를 분석(리플렉션)해서 set함수 호출(username, password).
						Object dtoInstance = params[0].getType().newInstance(); // JoinDto or LoginDto
						setData(dtoInstance, request);
						path = (String) method.invoke(userController, dtoInstance);	
					} else {
						path = (String) method.invoke(userController);	
					}
						
					RequestDispatcher dis = request.getRequestDispatcher(path); // 필터를 다시 안탐!! 내부적으로 동작함.
					dis.forward(request, response);
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			}			
		}
		
		if (isMatching == false) { // 주소 값이 없거나 이상한 주소가 들어왔을 경우.
				response.setContentType("text/html; charset=utf-8");
				PrintWriter out = response.getWriter();
				out.println("잘못된 주소 요청입니다. 404 에러");
				out.flush();
		}
	}
	
	private <T> void setData(T instance, HttpServletRequest request) { // Generic 가져오는 타입 그대로 적용.
		Enumeration<String> keys = request.getParameterNames(); // 크기 : 2(username, password)
		while(keys.hasMoreElements()) { // 열거형. 다음 값이 있으면? 2번 돈다. 
			String key = (String) keys.nextElement();
			String methodKey = keyToMethodKey(key); // setUsername
			
			Method[] methods = instance.getClass().getDeclaredMethods(); // 선언된 메서드 찾기. // 5개, toString, getter, setter
			
			for (Method method : methods) {
				if (method.getName().equals(methodKey)) {
					try {
						method.invoke(instance, request.getParameter(key)); // 무조건 String 값으로 리턴.
					} catch (Exception e) {
						e.printStackTrace();
					}
					break;
				}
			}
		}
	}

	private String keyToMethodKey(String key) { // setUsername
		String firstKey = "set";
		String upperKey = key.substring(0,1).toUpperCase();
		String remainKey = key.substring(1);

		return firstKey + upperKey + remainKey;

	}
}
