package com.cos.book.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {
	
	@GetMapping("/")
	public ResponseEntity<?> findAll() { // <?> 어떤 타입을 리턴할지 모름, 어떤 타입이든 리턴 가능,
		return new ResponseEntity<String>("ok", HttpStatus.OK);
	}
}
