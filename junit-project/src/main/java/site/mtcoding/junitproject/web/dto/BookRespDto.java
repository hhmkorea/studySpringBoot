package site.mtcoding.junitproject.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import site.mtcoding.junitproject.domain.Book;

@NoArgsConstructor
@Getter
public class BookRespDto {
    private Long id;
    private String title;
    private String author;

    public BookRespDto toDto(Book bookPs) {
        this.id  = bookPs.getId();
        this.title = bookPs.getTitle();
        this.author = bookPs.getAuthor();

        return this;
    }
}
