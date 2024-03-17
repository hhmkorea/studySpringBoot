package site.mtcoding.junitproject.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import site.mtcoding.junitproject.service.BookService;
import site.mtcoding.junitproject.web.dto.response.BookListRespDto;
import site.mtcoding.junitproject.web.dto.response.BookRespDto;
import site.mtcoding.junitproject.web.dto.request.BookSaveReqDto;
import site.mtcoding.junitproject.web.dto.response.CMRespDto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor // IoC 컨테이너에 있는걸 DI 해줌.
@RestController
public class BookApiController { // Composition = has 관계

    private final BookService bookService;

    // 1. 책 등록
    // key=value&key=value : Spring 기본 Parsing 전략.
    // { "key": value, "key": value } : JSON 타입으로 DB에 데이타 전송.
    @PostMapping("/api/v1/book")
    public ResponseEntity<?> saveBook(@RequestBody @Valid BookSaveReqDto bookSaveReqDto, BindingResult bindingResult) {

        // AOP 처리하는 게 좋음!!
        if (bindingResult.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();
            for (FieldError fe : bindingResult.getFieldErrors()) {
                errorMap.put(fe.getField(), fe.getDefaultMessage());
            }
            System.out.println("========= errorMap =============");
            System.out.println(errorMap.toString());

            throw new RuntimeException(errorMap.toString());
        }
        BookRespDto bookRespDto = bookService.saveBook(bookSaveReqDto);

        return new ResponseEntity<>( CMRespDto.builder().code(1).msg("글 저장 성공").body(bookRespDto).build(), HttpStatus.CREATED); // 201 = insert
    }

    @PostMapping("/api/v2/book")
    public ResponseEntity<?> saveBookV2(@RequestBody  BookSaveReqDto bookSaveReqDto) {
        // 사용자가 입력한 값을 받아서 Controller에 전달하는 역할을 제대로 수행 못함.

        BookRespDto bookRespDto = bookService.saveBook(bookSaveReqDto);

        return new ResponseEntity<>( CMRespDto.builder().code(1).msg("글 저장 성공").body(bookRespDto).build(), HttpStatus.CREATED); // 201 = insert
    }

    // 2. 책 목록보기
    @GetMapping("/api/v1/book")
    public ResponseEntity<?> getBookList() {
        BookListRespDto bookListRespDto =  bookService.findAllBooks();

        return new ResponseEntity<>( CMRespDto.builder().code(1).msg("글 목록보기 성공").body(bookListRespDto).build(), HttpStatus.OK); // 200 = ok

    }

    // 3. 책 한건보기
    @GetMapping("/api/v1/book/{id}")
    public ResponseEntity<?> getBookOne(@PathVariable Long id) {
        BookRespDto bookRespDto = bookService.findOneBook(id);
        return new ResponseEntity<>( CMRespDto.builder().code(1).msg("글 한건보기 성공").body(bookRespDto).build(), HttpStatus.OK); // 200 = ok;
    }

    // 4. 책 삭제하기
    @DeleteMapping("/api/v1/book/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return new ResponseEntity<>( CMRespDto.builder().code(1).msg("글 삭제하기 성공").body(null).build(), HttpStatus.OK); // 200 = ok;
    }

    // 5. 책 수정하기
    @PutMapping("/api/v1/book/{id}")
    public ResponseEntity<?> updateBook(@PathVariable Long id, @RequestBody @Valid BookSaveReqDto bookSaveReqDto, BindingResult bindingResult) {
        // @RequestBody : Spring에서 JSON 타입으로 받을 때 쓰임.
        // @Valid : Validation 체크(유효성 검사)

        // AOP 처리하는 게 좋음!!
        if (bindingResult.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();
            for (FieldError fe : bindingResult.getFieldErrors()) {
                errorMap.put(fe.getField(), fe.getDefaultMessage());
            }

            throw new RuntimeException(errorMap.toString());
        }

        BookRespDto bookRespDto = bookService.updateBook(id, bookSaveReqDto);
        return new ResponseEntity<>( CMRespDto.builder().code(1).msg("글 수정하기 성공").body(bookRespDto).build(), HttpStatus.OK); // 200 = ok;

    }
}
