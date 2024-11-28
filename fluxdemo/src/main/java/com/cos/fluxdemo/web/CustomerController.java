package com.cos.fluxdemo.web;

import java.time.Duration;

import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cos.fluxdemo.domain.CustomerRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import com.cos.fluxdemo.domain.Customer;

@RestController
public class CustomerController {

	private final CustomerRepository customerRepository;
	private final Sinks.Many<Customer> sink;
	
	/* Sink?
	    A 요청 -> Flux -> Stream
	    B 요청 -> Flux -> Stream
	    -> Flxux.merge -> sink  	*/
	
	public CustomerController(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
		sink = Sinks.many().multicast().onBackpressureBuffer();
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
	
	/*
	 * @GetMapping(value = "/customer/sse", produces =
	 * MediaType.TEXT_EVENT_STREAM_VALUE) // Server Sent Event, 1건씩 data() 형태로 바로바로
	 * 보여줌. 데이터가 없으면 종료. public Flux<Customer> findAllSSE() { return
	 * customerRepository.findAll().delayElements(Duration.ofSeconds(1)).log(); }
	 */
	
	@GetMapping("/customer/sse") // 생략, produces = MediaType.TEXT_EVENT_STREAM_VALUE) 
	public Flux<ServerSentEvent<Customer>> findAllSSE() {
		return sink.asFlux().map(c -> ServerSentEvent.builder(c).build()).doOnCancel(() -> { // response(응답) 취소 될때 
			sink.asFlux().blockLast(); // 마지막 데이터가 호출됨. 
		});
	}

	@PostMapping("/customer")
	public Mono<Customer> save() {
		return customerRepository.save(new Customer("gildong", "Hong")).doOnNext(c -> {
			sink.tryEmitNext(c); // Publisher에 데이터가 하나 추가됨.
		});
	}
	
}
