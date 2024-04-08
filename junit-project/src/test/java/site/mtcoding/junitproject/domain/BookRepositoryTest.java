package site.mtcoding.junitproject.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

@ActiveProfiles("dev")
@DataJpaTest // DB 와 관련된 컴포넌트만 메모리에 로딩
public class BookRepositoryTest {

    @Autowired // DI
    private BookRepository bookRepository;

    //@BeforeAll // 테스트 시작 전에 한번만 실행
    @BeforeEach // 각 테스트 시작 전에 한번씩 실행
    public void prepareData(){
        String title = "junit";
        String author = "겟인데어";
        Book book = Book.builder()
                .title(title)
                .author(author)
                .build();
        Book bookPS = bookRepository.save(book);
        System.out.println("------------------ prepareData : " + bookPS.getId());
    } // 트랜잭션 종료 됐다면 말이 안됨 :
    // 가정 1 : [ prepareData() + 1 save ] (T종료) , [ prepareData() + 2 findAll ] (T종료) -> 사이즈 1 (검증 완료)
    // 가정 2 : [ prepareData() + 1 save + prepareData() + 2 findAll ] (T종료) -> 사이즈 2 (검증 실패)

    // 1. 책 등록
    @Test
    public void save_test() {
        // given (데이터 준비)
        String title = "junit5";
        String author = "메타코딩";
        Book book = Book.builder()
                .title(title)
                .author(author)
                .build();
        // when (테스트 실행)
        Book bookPS = bookRepository.save(book); // PS:Persisternce 영속화, DB에 저장된것

        // then (검증)
        Assertions.assertEquals(title, bookPS.getTitle());
        Assertions.assertEquals(author, bookPS.getAuthor());
    } // transaction 종료(저장된 데이터를 초기화함)

    // 2. 책 목록보기
    @Test
    public void findAll_test() {
        // given (데이터 준비)
        String title = "junit";
        String author = "겟인데어";
        // when (테스트 실행)
        List<Book> bookPS = bookRepository.findAll();

        System.out.println(" 사이즈: ===================================    : "+bookPS.size());
        // then (검증)
        Assertions.assertEquals(title, bookPS.get(0).getTitle());
        Assertions.assertEquals(author, bookPS.get(0).getAuthor());
    } // transaction 종료(저장된 데이터를 초기화함)

    // 3. 책 한건보기
    @Sql("classpath:db/tableInit.sql")// auto_increment 때문에 drop table, create table 수행
    @Test
    public void findOne_test() {
        // given (데이터 준비)
        String title = "junit";
        String author = "겟인데어";
        // when (테스트 실행)
        Book bookPS = bookRepository.findById(1L).get();

        // then (검증)
        Assertions.assertEquals(title, bookPS.getTitle());
        Assertions.assertEquals(author, bookPS.getAuthor());
    } // transaction 종료(저장된 데이터를 초기화함)

    // 4. 책 삭제
    @Sql("classpath:db/tableInit.sql")
    @Test
    public void delete_test() {
        // given
        Long id = 5L;
        System.out.println("------------------ delete_test : " + id);
        // when
        bookRepository.deleteById(id);
        // then
        Assertions.assertFalse(bookRepository.findById(id).isPresent()); // 값이 없으면 false
    }

    // prepareData() 실행으로 데이타 들어가 있음: Junit, 겟인데어
    // 5. 책 수정
    @Sql("classpath:db/tableInit.sql")
    @Test
    public void update_test() {
        // given
        Long id = 1L;
        String title = "junit5";
        String author = "메타코딩";
        Book book = new Book(id, title, author);

        // when
//        bookRepository.findAll().stream()
//                .forEach((b) -> {
//                    System.out.println(b.getId());
//                    System.out.println(b.getTitle());
//                    System.out.println(b.getAuthor());
//                    System.out.println("========= 1 ============");
//                });

        Book bookPS = bookRepository.save(book);

//        bookRepository.findAll().stream()
//                .forEach((b) -> {
//                    System.out.println(b.getId());
//                    System.out.println(b.getTitle());
//                    System.out.println(b.getAuthor());
//                    System.out.println("========= 2 ============");
//                });

        // then
        Assertions.assertEquals(id, bookPS.getId());
        Assertions.assertEquals(title, bookPS.getTitle());
        Assertions.assertEquals(author, bookPS.getAuthor());
    }
}
