package com.cos.book.service;

import java.util.List;
import java.util.function.Supplier;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.book.domain.Book;
import com.cos.book.domain.BookRepository;

import lombok.RequiredArgsConstructor;

// 기능을 정의할 수 있고, Transaction을 관리할 수 있음.
@RequiredArgsConstructor // final이 들어있는 변수의 생성자 자동으로 만들어줌, 자동으로 DI(의존성 주입)됨.
@Service
public class BookService {

	private final BookRepository bookRepository;
	
	@Transactional // 서비스 함수가 종료될 때 commit할지 rollback Transaction 관리하겠다.
	public Book save(Book book) {
		return bookRepository.save(book);
	}
	
	@Transactional(readOnly = true) 
	/*  @Transactional(readOnly = true) 
	 	1. 장점 : 
	 	  JPA 변경감지라는 내부 기능 활성화 X, 내부연산 감소시킴,
	 	  update시의 정합성을 유지해줌,
	 	2. 단점 :
	  	  insert의 유령데이터현상(펜텀현상) 못막음.
	 */
	public Book findbyOne(Long id) {
		return bookRepository.findById(id)
				.orElseThrow( () ->  new IllegalArgumentException("ID를 찾아주세요."));
	}

	@Transactional(readOnly = true) 
	public List<Book> findAll() {
		return bookRepository.findAll();
	}
	
	@Transactional
	public Book update(Long id, Book book) {
		// 더티체킹(Dirty Checking) update 치기
		Book bookEntity = bookRepository.findById(id)
				.orElseThrow( () ->  new IllegalArgumentException("ID를 찾아주세요.")); 
		// ===> 영속화(book Object) -> 영속성 컨텍스트(Persistance Context)에 보관. spring 메모리 공간에 따로 갖고 있게됨.
		bookEntity.setTitle(book.getTitle());
		bookEntity.setAuthor(book.getAuthor());		
		return bookEntity;
	} // 함수 종료 => Transaction 종료 => 영속화 되어있는 데이터를 갱신(flush) => commit ======> 더티체킹 
	
	@Transactional
	public String delete(Long id) {
		bookRepository.deleteById(id); // 오류가 터지면 Exception 타니까 신경쓰지 말고 
		return "ok";
	}
}
