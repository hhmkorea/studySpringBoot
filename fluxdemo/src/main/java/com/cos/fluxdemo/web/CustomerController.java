package com.cos.fluxdemo.web;

import java.time.Duration;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.cos.fluxdemo.domain.CustomerRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import com.cos.fluxdemo.domain.Customer;

@RestController
public class CustomerController {

	private final CustomerRepository customerRepository;
	
	public CustomerController(CustomerRepository customerRepository) {
		super();
		this.customerRepository = customerRepository;
	}
	
	@GetMapping("/flux")
	public Flux<Integer> flux() {
		return Flux.just(1,2,3,4,5).delayElements(Duration.ofSeconds(1)).log(); // 데이터 모아놨다가 한번에 5초 지나서 응답.
	}
	
	@GetMapping(value = "/fluxstream", produces = MediaType.APPLICATION_STREAM_JSON_VALUE) // 1건 onNext할때마다 Buffer로 flush 해줌.
	public Flux<Integer> fluxstream() {
		return Flux.just(1,2,3,4,5).delayElements(Duration.ofSeconds(1)).log(); //  response(응답) 유지하면서 건건이 계속 데이터 받음. 5초 지나서 응답.
	}
	
	@GetMapping(value = "/customer", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)  // 1건 onNext할때마다 Buffer로 flush 해줌. 데이터가 소진되면 response(응답) 종료됨.
	public Flux<Customer> findAll() { // 여러 건 있을때 
		return customerRepository.findAll().delayElements(Duration.ofSeconds(1)).log();
	}
	
	@GetMapping("/customer/{id}") 
	public Mono<Customer> findById(@PathVariable Long id) { // 한 건만 있을때 
		return customerRepository.findById(id).log();
	}
}
