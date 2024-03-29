package shop.mtcoding.bank.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@Sql("classpath:db/teardown.sql") // 실행시점 : BeforeEach 실행 직전마다!!
@ActiveProfiles("test") // 쿼리 파라매터값까지 로그로 보여줌.
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class SecurityConfigTest {

    // 가짜 환경에 등록된 MockMvc를 DI함.
    @Autowired
    private MockMvc mvc;

    // 서버는 일관성있게 에러가 리턴되어야 한다.
    // 내가 모르는 에러가 front에 날라가지 않게 내가 직접 다 제어하자!
    @Test
    public void authentication_test() throws Exception {
        // given

        // when
        ResultActions resultActions = mvc.perform(get("/api/s/hello"));
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        int httpStatusCode = resultActions.andReturn().getResponse().getStatus();
        System.out.println("테스트: " + responseBody);
        System.out.println("테스트: " + httpStatusCode);

        // then
        assertThat(httpStatusCode).isEqualTo(401); // 401 : 인증이 안된거, 403 : 권한이 없는거
    }

    @Test
    public void authorization_test() throws Exception {
        // given

        // when
        ResultActions resultActions = mvc.perform(get("/api/admin/hello"));
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        int httpStatusCode = resultActions.andReturn().getResponse().getStatus();
        System.out.println("테스트: " + responseBody);
        System.out.println("테스트: " + httpStatusCode);

        // then
        assertThat(httpStatusCode).isEqualTo(401); // 401 : 인증이 안된거, 403 : 권한이 없는거
    }
}
