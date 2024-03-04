package com.cos.book.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.book.domain.Book;
import com.cos.book.service.BookService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor // final 변수의 생성자를 만들어서 받아줌.
@RestController
public class BookController {
	
	private final BookService bookService;
	
	@PostMapping("/book")
	public ResponseEntity<?> save(@RequestBody Book book) { // @RequestBody : json 형태로 데이타 받음.		
		// List<Book> abc = new ArrayList<Book>();
		// java 1.6이상은 new ArrayList<>() 만 넣어도 됨.
		//<?>은 리턴될때 데이터 타입이 정해짐.
		return new ResponseEntity<>(bookService.save(book), HttpStatus.CREATED);
		// ResponseEntity : book타입 데이타랑 HttpStatus도 함께 담아서 리턴함.
	}
	
	@GetMapping("/book")
	public ResponseEntity<?> findAll() { // <?> 어떤 타입을 리턴할지 모름, 어떤 타입이든 리턴 가능,
		return new ResponseEntity<>(bookService.findAll(), HttpStatus.OK); // 200 
	}	

	@GetMapping("/book/{id}")
	public ResponseEntity<?> findById(@PathVariable Long id) {
		return new ResponseEntity<>(bookService.findbyOne(id), HttpStatus.OK); 
	}

	@PutMapping("/book/{id}")
	public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Book book) { 
		return new ResponseEntity<>(bookService.update(id, book), HttpStatus.OK);
	}
	
	@DeleteMapping("/book/{id}")
	public ResponseEntity<?> deleteById(@PathVariable Long id) {
		return new ResponseEntity<>(bookService.delete(id), HttpStatus.OK);
	}
}
