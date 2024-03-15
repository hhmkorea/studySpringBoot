package site.mtcoding.junitproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.mtcoding.junitproject.domain.Book;
import site.mtcoding.junitproject.domain.BookRepository;
import site.mtcoding.junitproject.web.dto.BookRespDto;
import site.mtcoding.junitproject.web.dto.BookSaveReqDto;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor // final 지정시 같이 넣음.
@Service
public class BookService {

    private final BookRepository bookRepository;
    // 1. 책 등록
    @Transactional(rollbackFor = RuntimeException.class)
    public BookRespDto saveBook(BookSaveReqDto dto) {
        // 영속화된(DB에 저장된) Dto가 빠져나가지 못하게하고 서비스 단 안에서 움직이는 응답한 Dto 지정.
        // 영속화된 Dto를 다시 return하면 LazyLoding 현상 발생우려.
        Book bookPS = bookRepository.save(dto.toEntity());
        return new BookRespDto().toDto(bookPS);
    }

    // 2. 책 목록 보기
    public List<BookRespDto> findAllBooks() {
        return bookRepository.findAll().stream()
                .map(new BookRespDto()::toDto) // 자바 1.8 메서드 참조 // (bookPS) -> new BookRespDto().toDto(bookPS)
                .collect(Collectors.toList());
    }

    // 3. 책 한건 보기
    public BookRespDto findOneBook(Long id) {
        Optional <Book> bookOP = bookRepository.findById(id);
        if(bookOP.isPresent()) {// 찾았다면
            return new BookRespDto().toDto(bookOP.get());
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
    public void updateBook(Long id, BookSaveReqDto dto) {
        Optional <Book> bookOP = bookRepository.findById(id);
        if(bookOP.isPresent()) {// 찾았다면
            Book bookPS = bookOP.get();
            bookPS.update(dto.getTitle(), dto.getAuthor());
        } else {
            throw new RuntimeException("해당 아이디를 찾을 수 없습니다.");
        }
    }// 메서드 종료시에 더티체킁(flush)로 update 합니다.

}
