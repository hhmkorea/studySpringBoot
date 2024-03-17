package site.mtcoding.junitproject.web.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
public class BookListRespDto {
    List<BookRespDto> items;

    @Builder // Constructor 생성 후 붙임.
    public BookListRespDto(List<BookRespDto> bookList) {
        this.items = bookList;
    }
}
