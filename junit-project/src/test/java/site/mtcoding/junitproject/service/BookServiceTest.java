package site.mtcoding.junitproject.service;

import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;
import site.mtcoding.junitproject.domain.Book;
import site.mtcoding.junitproject.domain.BookRepository;
import site.mtcoding.junitproject.util.MailSender;
import site.mtcoding.junitproject.web.dto.response.BookListRespDto;
import site.mtcoding.junitproject.web.dto.response.BookRespDto;
import site.mtcoding.junitproject.web.dto.request.BookSaveReqDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ActiveProfiles("dev")
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
        assertThat(bookRespDto.getTitle()).isEqualTo(dto.getTitle());
        assertThat(bookRespDto.getAuthor()).isEqualTo(dto.getAuthor());
    }

    // 체크포인트
    @Test
    public void findAllBooks_test() {
        // given(파라매터로 들어올 데이터)

        // stub(가설)
        List<Book> books = new ArrayList<>();
        books.add(new Book(1L, "junit강의", "메타코딩"));
        books.add(new Book(2L, "spring강의", "겟인데어"));
        Mockito.when(bookRepository.findAll()).thenReturn(books);

        // when(실행)
        BookListRespDto bookListRespDto = bookService.findAllBooks();

        // then(검증)
        assertThat(bookListRespDto.getItems().get(0).getTitle()).isEqualTo("junit강의");
        assertThat(bookListRespDto.getItems().get(0).getAuthor()).isEqualTo("메타코딩");
        assertThat(bookListRespDto.getItems().get(1).getTitle()).isEqualTo("spring강의");
        assertThat(bookListRespDto.getItems().get(1).getAuthor()).isEqualTo("겟인데어");
    }

    @Test
    public void findOneBook_test() {
        // given
        Long id = 1L;

        // stub
        Book book = new Book(1L, "junit강의", "메타코딩");
        Optional<Book> bookOP = Optional.of(book);
        Mockito.when(bookRepository.findById(id)).thenReturn(bookOP);

        // when
        BookRespDto bookRespDto = bookService.findOneBook(id);

        // then
        assertThat(bookRespDto.getTitle()).isEqualTo(book.getTitle());
        assertThat(bookRespDto.getAuthor()).isEqualTo(book.getAuthor());
    }

    @Test
    public void updateBook_test() {
        // given
        Long id = 1L;
        BookSaveReqDto dto = new BookSaveReqDto();
        dto.setTitle("spring강의"); // junit강의
        dto.setAuthor("겟인데어"); // 메타코딩

        // stub
        Book book = new Book(1L, "junit강의", "메타코딩");
        Optional<Book> bookOP = Optional.of(book);
        Mockito.when(bookRepository.findById(id)).thenReturn(bookOP);

        // when
        BookRespDto bookRespDto = bookService.updateBook(id, dto);

        // then
        assertThat(bookRespDto.getTitle()).isEqualTo(dto.getTitle());
        assertThat(bookRespDto.getAuthor()).isEqualTo(dto.getAuthor());

    }
    // given

    // stub

    // when

    // then
}
