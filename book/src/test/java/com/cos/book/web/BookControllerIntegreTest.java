package com.cos.book.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

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
}
