package com.cos.book.domain;

import org.springframework.data.jpa.repository.JpaRepository;

// @Repostitory 적어야 스프링 Ioc에 Bean으로 등록 되는데!!!
// JpaRepository를 extends하면 생략 가능, 자동으로 Bean 생성해줌!!!
// JpaRepository는 CRUD 함수를 들고 있음.
public interface BookRepository extends JpaRepository<Book, Long>{

}
