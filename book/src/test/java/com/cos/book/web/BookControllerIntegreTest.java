package com.cos.book.web;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.transaction.annotation.Transactional;

import com.cos.book.domain.Book;
import com.cos.book.domain.BookRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.EntityManager;

/*
 * 
 * 통합테스트 : Controller로 전체 Spring 테스트, (모든 Bean들을 똑같이 Ioc 올리고 테스트 하는 것)
 * WebEnvironment.MOCK : 실제 톰켓을 올리는게 아니라, 다른 톰켓으로 테스트 
 * WebEnvironment.RANDOM_PORT : 실제 톰켓으로 테스트 
 * @AutoConfigureMockMvc : MockMvc를 Ioc에 등록해줌.
 * @Transactional : 각각의 테스트 함수가 종료될때마다 트랜잭션을 rollback 해주는 anotation
 */

@Transactional
@AutoConfigureMockMvc 
@SpringBootTest(webEnvironment = WebEnvironment.MOCK) 
public class BookControllerIntegreTest {

	@Autowired // MVC 테스트
	private MockMvc mockMvc; 
	
	@Autowired
	private BookRepository bookRepository;
	
	@Autowired
	private EntityManager entityManager; // JPA가 EntityManager의 최종 구현체 
	
	@BeforeEach	// 모든 테스트가 실행되기 전에 각각 한번씩 실행됨. (JUnit4는 @Before)
	public void init() {
		entityManager.createNativeQuery("ALTER TABLE book ALTER COLUMN id RESTART WITH 1").executeUpdate();
	}
	
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

	@Test
	public void findAll_Test() throws Exception {
		// given  
		List<Book> books = new ArrayList<>();
		books.add(new Book(null, "스프링부트 따라하기", "코스"));
		books.add(new Book(null, "리엑트 따라하기", "코스"));		
		books.add(new Book(null, "JUnit 따라하기", "코스"));		
		// 딱딱 분리해서 하나씩 테스트 
		bookRepository.saveAll(books); // 실제 DB에 데이타 넣기 
		
		// when  
		ResultActions resultAction = mockMvc.perform(get("/book")
				.accept(MediaType.APPLICATION_JSON_UTF8));
		
		// then 
		resultAction
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", Matchers.hasSize(3)))
				.andExpect(jsonPath("$.[2].title").value("JUnit 따라하기"))
				.andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void findById_Test() throws Exception {
		// given
		Long id = 2L;
		
		List<Book> books = new ArrayList<>();
		books.add(new Book(null, "스프링부트 따라하기", "코스"));
		books.add(new Book(null, "리엑트 따라하기", "코스"));		
		books.add(new Book(null, "JUnit 따라하기", "코스"));		
		bookRepository.saveAll(books); // 실제 DB에 모두 저장
		
		// when 
		ResultActions resultAction = mockMvc.perform(get("/book/{id}", id)
				.accept(MediaType.APPLICATION_JSON_UTF8));
		
		// then
		resultAction
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.title").value("리엑트 따라하기"))
		.andDo(MockMvcResultHandlers.print());
	}
	
	@Test
	public void update_Test() throws Exception {
		// given
		Long id = 3L;
		List<Book> books = new ArrayList<>();
		books.add(new Book(null, "스프링부트 따라하기", "코스"));
		books.add(new Book(null, "리엑트 따라하기", "코스"));		
		books.add(new Book(null, "JUnit 따라하기", "코스"));		
		bookRepository.saveAll(books); // 실제 DB에 모두 저장
		
		Book book = new Book(null, "C++ 따라하기", "코스");
		String content = new ObjectMapper().writeValueAsString(book); 
		
		// when 
		ResultActions resultAction = mockMvc.perform(put("/book/{id}", id)
				.contentType(MediaType.APPLICATION_JSON_UTF8) // 던질 데이타타입 : application/json, json 타입. 
				.content(content)																	// 던질 데이타 
				.accept(MediaType.APPLICATION_JSON_UTF8));  		// 응답 : application/json, json 타입. 
				
		// then
		resultAction
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.id").value(3L))
		.andExpect(jsonPath("$.title").value("C++ 따라하기"))
		.andDo(MockMvcResultHandlers.print());
	}
	
	@Test
	public void delete_Test() throws Exception {
		// given
		Long id = 1L;

		List<Book> books = new ArrayList<>();
		books.add(new Book(null, "스프링부트 따라하기", "코스"));
		books.add(new Book(null, "리엑트 따라하기", "코스"));		
		books.add(new Book(null, "JUnit 따라하기", "코스"));		
		bookRepository.saveAll(books); // 실제 DB에 모두 저장

		bookRepository.deleteById(id); // 데이타 삭제 

		// when 
		ResultActions resultAction = mockMvc.perform(delete("/book/{id}", id)
				.accept(MediaType.TEXT_PLAIN));  		// 응답 : 문자 리턴 
				
		// then
		resultAction
				.andExpect(status().isOk())
				.andDo(MockMvcResultHandlers.print());
		
		// 문자 리턴할때 넣어야 함.
		MvcResult requestResult = resultAction.andReturn();
		String result = requestResult.getResponse().getContentAsString();
		
		assertEquals("ok", result);
	}
	
	
}
