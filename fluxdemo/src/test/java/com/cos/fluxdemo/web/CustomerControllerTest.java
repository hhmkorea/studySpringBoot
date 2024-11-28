package com.cos.fluxdemo.web;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.cos.fluxdemo.domain.Customer;
import com.cos.fluxdemo.domain.CustomerRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

// 통합 테스트 
//@SpringBootTest // spring에 관련된 모든 Context를 메모리에 띄움.
@WebFluxTest // webClient가 자동으로 Bean 등록됨. 
@AutoConfigureWebTestClient
public class CustomerControllerTest { // Controller Test
	
	@MockBean // bean 등록됨, 가짜 객체 띄우기. 인테페이스도 등록가능.
	//@Autowired
	private CustomerRepository repository;
	
	@Autowired
	private WebTestClient webClient; // 비동기로 http 요청 
	
	@Test // 필요한것만 메모리에 띄워 테스트
	public void findById_test() { // 한건 찾기
		
		// given 
		Mono<Customer> givenData = Mono.just(new Customer("Han", "Sophie"));
		
		// stub -> 행동 지시
		when(repository.findById(1L)).thenReturn(givenData);
		
		webClient.get().uri("/customer/{id}", 1L)
			.exchange()
			.expectBody()
			.jsonPath("$.firstName").isEqualTo("Han")
			.jsonPath("$.lastName").isEqualTo("Sophie");
				
	}
	
/*	@Test 
	public void findById_test() { // 한건 찾기
		System.out.println("======================");
		//repository.findAll().log();
		Flux<Customer> fCustomer = repository.findAll();
		fCustomer.subscribe((c) -> System.out.println("데이터 : " + c));
	}*/
}
