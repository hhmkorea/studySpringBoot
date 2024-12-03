package com.cos.photogramstart.web;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.service.AuthService;
import com.cos.photogramstart.web.dto.auth.SignupDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor // B) final 필드를 DI(의존성 주입) 할때 사용
@Controller // 1. Ioc 등록 2. 파일을 리턴하는 컨트롤러
public class AuthController {
	
	private static final Logger log = LoggerFactory.getLogger(AuthController.class);
	
	private final  AuthService authService; // A)
	
	// ID : 의존성 주입, 번거로워서 A),B)방식을 주로 사용함.
//	private AuthService authService;
//	public AuthController(AuthService authService) { 
//		this.authService = authService;
//	}

	@GetMapping("/auth/signin")
	public String signinForm() {
		return "auth/signin";
	}

	@GetMapping("/auth/signup")
	public String signupForm() {
		return "auth/signup";
	}
	
	// 회원가입버튼 -> /auth/signup -> /auth/signin
	// 회원가입버튼 X
	@PostMapping("/auth/signup")
	public  String signup(@Valid SignupDto signupDto, BindingResult bindingResult) { 
		// key=value (x-www-form-urlencoded)
		// @ResponseBody 데이터를 리턴함.

		// 유효성 검사하는 전처리!! --> AOP로 공통으로 뺌 
		log.info(signupDto.toString());
		User user = signupDto.toEntity();
		authService.joinMember(user); // 회원가입
		
		// 로그를 남기는 후처리!!! 
		return "auth/signin"; // 로그인
	
	}
}
