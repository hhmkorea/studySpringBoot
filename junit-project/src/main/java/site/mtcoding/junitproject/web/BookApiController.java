package site.mtcoding.junitproject.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import site.mtcoding.junitproject.service.BookService;
import site.mtcoding.junitproject.web.dto.response.BookRespDto;
import site.mtcoding.junitproject.web.dto.request.BookSaveReqDto;
import site.mtcoding.junitproject.web.dto.response.CMRespDto;

@RequiredArgsConstructor // IoC 컨테이너에 있는걸 DI 해줌.
@RestController
public class BookApiController { // Composition = has 관계

    private final BookService bookService;

    // 1. 책 등록
    // key=value&key=value : Spring 기본 Parsing 전략.
    // { "key": value, "key": value } : JSON 타입으로 DB에 데이타 전송.
    @PostMapping("/api/v1/book")
    public ResponseEntity<?> saveBook(@RequestBody BookSaveReqDto bookSaveReqDto) {
        BookRespDto bookRespDto = bookService.saveBook(bookSaveReqDto);
        CMRespDto<?> cmRespDto = CMRespDto.builder().code(1).msg("글 저장 성공").body(bookRespDto).build();
        return new ResponseEntity<>(cmRespDto, HttpStatus.CREATED); // 201 = insert
    }

    // 2. 책 목록보기
    public ResponseEntity<?> getBookList() {
        return null;

    }

    // 3. 책 한건보기
    public ResponseEntity<?> getBookOne() {
        return null;

    }

    // 4. 책 삭제하기
    public ResponseEntity<?> deleteBook() {
        return null;

    }

    // 5. 책 수정하기
    public ResponseEntity<?> updateBook() {
        return null;

    }
}
