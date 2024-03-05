package com.cos.book.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cos.book.domain.Book;
import com.cos.book.domain.BookRepository;

/**
 * 단위 테스트 ( Service와 관련된 것만 IoC에 올리고 테스트 하는것. 
 * BookRepository => 가짜 객체로 만들 수 있음. --> MockitoExtension이 지원해줌.
 */
@ExtendWith(MockitoExtension.class) 
public class BookServiceUnitTest {

	@InjectMocks // BookService 객체가 만들어 질때 해당 파일에 @Mock로 등록된 모든 애들을 주입받는다.
	//@Mock // Spring이 아니라 Mockito 메모리 공간에 뜸. 가짜로 객체를 띄우는 것. Ioc가 아님.
	private BookService bookService;
	
	@Mock
	private BookRepository bookRepository;
	
	@Test
	public void save_Test() { // 전부 가짜 데이터
//		
//		// BODMocikto 방식
//		// given
//		Book book = new Book();
//		book.setTitle("책제목1");
//		book.setAuthor("책저자1");
//		
//		// stub - 동작 지정
//		when(bookRepository.save(book)).thenReturn(book);
//		
//		// test execute
//		Book bookEntity = bookService.save(book);
//		
//		// then
//		assertEquals(bookEntity, book);
	}
}
