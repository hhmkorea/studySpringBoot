package site.mtcoding.junitproject.web;

import site.mtcoding.junitproject.domain.Book;

public class BookApiControllerTest {
    public void test() {
        Book book = Book.builder()
                .title("안녕")
                .author("안녕")
                .build();
    }
}
