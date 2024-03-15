package site.mtcoding.junitproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.mtcoding.junitproject.domain.Book;
import site.mtcoding.junitproject.domain.BookRepository;
import site.mtcoding.junitproject.web.dto.BookRespDto;
import site.mtcoding.junitproject.web.dto.BookSaveReqDto;

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

    // 3. 책 한건 보기

    // 4. 책 삭제

    // 5. 책 수정
}
