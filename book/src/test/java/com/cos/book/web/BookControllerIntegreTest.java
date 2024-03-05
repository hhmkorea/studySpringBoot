package com.cos.book.web;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.transaction.annotation.Transactional;

import com.cos.book.domain.Book;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

/*
 * 
 * 통합테스트 : Controller로 전체 Spring 테스트, (모든 Bean들을 똑같이 Ioc 올리고 테스트 하는 것)
 * WebEnvironment.MOCK : 실제 톰켓을 올리는게 아니라, 다른 톰켓으로 테스트 
 * WebEnvironment.RANDOM_PORT : 실제 톰켓으로 테스트 
 * @AutoConfigureMockMvc : MockMvc를 Ioc에 등록해줌.
 * @Transactional : 각각의 테스트 함수가 종료될때마다 트랜잭션을 rollback 해주는 anotation
 */

@Slf4j
@Transactional
@AutoConfigureMockMvc 
@SpringBootTest(webEnvironment = WebEnvironment.MOCK) 
public class BookControllerIntegreTest {

	@Autowired // MVC 테스트
	private MockMvc mockMvc; 
	
	@Test
	public void save_Test() throws Exception {
		// given (테스트를 하기 위한 준비)
		Book book = new Book(null, "스프링 따라하기", "코스");
		String content = new ObjectMapper().writeValueAsString(book); // 실제 DB 저장됨, 통합테스트에서는 Stub이 필요없음.
		
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
