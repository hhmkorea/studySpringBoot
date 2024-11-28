package com.cos.fluxdemo.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.context.annotation.Import;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@DataR2dbcTest
@Import(DBinit.class)
public class CustomerRepositoryTest {
	
	@Autowired
	private CustomerRepository repository;
	
	@Test
	public void findById_Test() {
//		repository.findById(2L).subscribe((c) -> {
//			System.out.println(c);
//		});
		StepVerifier
			.create(repository.findById(2L))
			.expectNextMatches((c) -> {
				return c.getFirstName().equals("Chloe");
			})
			.expectComplete()
			.verify();
		
	}
}
