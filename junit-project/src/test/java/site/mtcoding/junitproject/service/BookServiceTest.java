package site.mtcoding.junitproject.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import site.mtcoding.junitproject.domain.BookRepository;
import site.mtcoding.junitproject.util.MailSender;
import site.mtcoding.junitproject.util.MailSenderStub;
import site.mtcoding.junitproject.web.dto.BookRespDto;
import site.mtcoding.junitproject.web.dto.BookSaveReqDto;

@DataJpaTest
public class BookServiceTest {

    @Autowired // DI
    private BookRepository bookRepository;

    // 문제점 -> 서비스만 테스트하고 싶은데 Repository 레이어가 함께 테스트 된다는 점!!
    @Test
    public void saveBook_test() {
        // given
        BookSaveReqDto dto = new BookSaveReqDto();
        dto.setTitle("JUnit강의");
        dto.setAuthor("메타코딩");
        // stub
        MailSenderStub mailSenderStub = new MailSenderStub();

        // when
        BookService bookService = new BookService(bookRepository, mailSenderStub);
        BookRespDto bookRespDto = bookService.saveBook(dto);

        // then
        Assertions.assertEquals(dto.getTitle(), bookRespDto.getTitle());
        Assertions.assertEquals(dto.getAuthor(), bookRespDto.getAuthor());
    }
}
