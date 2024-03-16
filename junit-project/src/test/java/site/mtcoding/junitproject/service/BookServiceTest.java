package site.mtcoding.junitproject.service;

import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import site.mtcoding.junitproject.domain.BookRepository;
import site.mtcoding.junitproject.util.MailSender;
import site.mtcoding.junitproject.web.dto.BookRespDto;
import site.mtcoding.junitproject.web.dto.BookSaveReqDto;

@ExtendWith(MockitoExtension.class) // 가짜 메모리 환경
public class BookServiceTest {

    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepository; // 가짜로 bookReopsitory 만들기

    @Mock
    private MailSender mailSender;

    // 문제점 -> 서비스만 테스트하고 싶은데 Repository 레이어가 함께 테스트 된다는 점!!
    @Test
    public void saveBook_test() {
        // given
        BookSaveReqDto dto = new BookSaveReqDto();
        dto.setTitle("JUnit강의");
        dto.setAuthor("메타코딩");

        // stub : 행동 정의, 가설
        Mockito.when(bookRepository.save(ArgumentMatchers.any())).thenReturn(dto.toEntity());
        Mockito.when(mailSender.send()).thenReturn(true);

        // when
        BookRespDto bookRespDto = bookService.saveBook(dto);

        // then
        //Assertions.assertEquals(dto.getTitle(), bookRespDto.getTitle());
        //Assertions.assertEquals(dto.getAuthor(), bookRespDto.getAuthor());
        assertThat(dto.getTitle()).isEqualTo(bookRespDto.getTitle());
        assertThat(dto.getAuthor()).isEqualTo(bookRespDto.getAuthor());
    }
}
