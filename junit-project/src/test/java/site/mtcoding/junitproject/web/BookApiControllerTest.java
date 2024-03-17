package site.mtcoding.junitproject.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import site.mtcoding.junitproject.service.BookService;
import site.mtcoding.junitproject.web.dto.request.BookSaveReqDto;

// 통합테스트 (Controller, Service, Repository)
// 전체를 메모리에 다 띄우고 테스트 진행.
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookApiControllerTest {

    @Autowired
    private TestRestTemplate rt;

    private static ObjectMapper om; // static 변수 : JVM 시작될때 최초로 메모리에 떠 있는것
    private static HttpHeaders headers;

    @BeforeAll
    public static void init() { // static 메서드
        om = new ObjectMapper();
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    @Test
    public void saveBook_test() throws Exception {
        // given
        BookSaveReqDto bookSaveReqDto = new BookSaveReqDto();
        bookSaveReqDto.setTitle("스프링1강");
        bookSaveReqDto.setAuthor("겟인데어");

        String body = om.writeValueAsString(bookSaveReqDto); // body데이터가 JSON으로 변경됨.

        // when
        HttpEntity<String> request = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = rt.exchange("/api/v1/book", HttpMethod.POST, request, String.class);

        // then
        DocumentContext dc = JsonPath.parse(response.getBody()); // DocumentContext : JSON 데이터 분석.
        String title = dc.read("$.body.title");
        String author = dc.read("$.body.author");

        Assertions.assertThat(title).isEqualTo("스프링1강");
        Assertions.assertThat(author).isEqualTo("겟인데어");
    }
}
