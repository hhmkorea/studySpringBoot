package site.mtcoding.junitproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.mtcoding.junitproject.domain.Book;
import site.mtcoding.junitproject.domain.BookRepository;
import site.mtcoding.junitproject.util.MailSender;
import site.mtcoding.junitproject.web.dto.BookRespDto;
import site.mtcoding.junitproject.web.dto.BookSaveReqDto;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor // final 지정시 같이 넣음.
@Service
public class BookService {

    private final BookRepository bookRepository;
    private final MailSender mailSender;

    // 1. 책 등록
    @Transactional(rollbackFor = RuntimeException.class)
    public BookRespDto saveBook(BookSaveReqDto dto) {
        Book bookPS = bookRepository.save(dto.toEntity());
        if (bookPS != null) {

            // 메일 보내기 메서드 호출 (return ture or false)
            if (!mailSender.send()) {
                throw new RuntimeException("메일이 전송되지 않았습니다.");
            }

        }
        return bookPS.toDto();
    }

    // 2. 책 목록 보기
    public List<BookRespDto> findAllBooks() {
        // 본코드에 문제가 있나?
        List<BookRespDto> dtos = bookRepository.findAll().stream()
                //.map((bookPS) -> bookPS.toDto()) // 람다식
                .map(Book::toDto)
                .collect(Collectors.toList());

        // print
        dtos.stream().forEach((dto) -> {
            System.out.println("============= 본코드 =============");
            System.out.println(dto.getId());
            System.out.println(dto.getTitle());
            System.out.println(dto.getAuthor());
        });
        return dtos;
    }

    // 3. 책 한건 보기
    public BookRespDto findOneBook(Long id) {
        Optional <Book> bookOP = bookRepository.findById(id);
        if(bookOP.isPresent()) {// 찾았다면
            Book bookPS = bookOP.get();
            return bookPS.toDto();
        } else {
            throw new RuntimeException("해당 아이디를 찾을 수 없습니다.");
        }
    }

    // 4. 책 삭제
    @Transactional(rollbackFor = RuntimeException.class)
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    // 5. 책 수정
    @Transactional(rollbackFor = RuntimeException.class)
    public BookRespDto updateBook(Long id, BookSaveReqDto dto) {
        Optional <Book> bookOP = bookRepository.findById(id);
        if(bookOP.isPresent()) {// 찾았다면
            Book bookPS = bookOP.get();
            bookPS.update(dto.getTitle(), dto.getAuthor()); // update 메서드 테스트를 못해본다.
            return bookPS.toDto();
        } else {
            throw new RuntimeException("해당 아이디를 찾을 수 없습니다.");
        }
    }// 메서드 종료시에 더티체킁(flush)로 update 합니다.

}
