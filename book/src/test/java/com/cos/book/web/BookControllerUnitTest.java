package com.cos.book.web;

import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.cos.book.domain.Book;
import com.cos.book.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

// 단위 테스트 : Controller만 테스트 (Controller 관련 로직만 띄우기)

@Slf4j
@WebMvcTest // 메모리에 Controller, Filter, ControllerAdvice 가 뜸.
// @ExtendWith(SpringExtension.class)이 안에 돌어있음. Spring 환경에서 테스트할때 필수! Junit4에는 붙여야함.
public class BookControllerUnitTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean // Ioc 환경에 bean 등록됨. 가짜서비스 올림.
	private BookService bookService;

	// BDDMockito 패턴 : given, when, then
	@Test
	public void save_Test() throws Exception {
		// given (테스트를 하기 위한 준비)
		Book book = new Book(null, "스프링 따라하기", "코스");
		String content = new ObjectMapper().writeValueAsString(book); // Object를 Json으로 바꿈. --- BookService.save()는 json 데이타로 해야함.
		when(bookService.save(book)).thenReturn(new Book(1L, "스프링 따라하기", "코스")); // stub : dummy데이타로 미리 실행후 리턴될 결과 지정함.
		
		// when (테스트 실행)
		ResultActions resultAction = mockMvc.perform(post("/book")
				.contentType(MediaType.APPLICATION_JSON_UTF8) // 던질 데이타타입 : application/json, json 타입. 
				.content(content)																	// 던질 데이타 
				.accept(MediaType.APPLICATION_JSON_UTF8));  		// 응답 : application/json, json 타입. 
		
		// then (검증)
		resultAction
				.andExpect(status().isCreated())											// http 응답코드 201 기대 
				.andExpect(jsonPath("$.title").value("스프링 따라하기"))	// $:전체결과, title 응답값  검하고 싶음.
				.andDo(MockMvcResultHandlers.print());
	}
}
