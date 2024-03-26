package shop.mtcoding.bank.config.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import shop.mtcoding.bank.config.dummy.DummyObject;
import shop.mtcoding.bank.domain.user.UserRepository;
import shop.mtcoding.bank.dto.user.UserReqDto.LoginReqDto;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
/**
 * packageName    : shop.mtcoding.bank.config.jwt
 * fileName       : JwtAuthenticationFilterTest
 * author         : dotdot
 * date           : 2024-03-26
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-03-26        dotdot       최초 생성
 */
// SpringBootTest 하는 곳에는 전부다 teardown.sql 을 붙여주자.
@Sql("classpath:db/teardown.sql") // 실행시점 : BeforeEach 실행 직전마다!!
@ActiveProfiles("test") // 쿼리 파라매터값까지 로그로 보여줌.
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
public class JwtAuthenticationFilterTest extends DummyObject {

    @Autowired
    private ObjectMapper om;
    @Autowired
    private MockMvc mvc;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() throws Exception {
        userRepository.save(newUser("ssar", "쌀"));
    }

    @Test
    public void successfulAuthentication_test() throws Exception {
        // given
        LoginReqDto loginReqDto = new LoginReqDto();
        loginReqDto.setUsername("ssar");
        loginReqDto.setPassword("1234");
        String reuquestBody = om.writeValueAsString(loginReqDto);
        System.out.println("테스트 : " + reuquestBody);

        // when
        ResultActions resultActions = mvc.perform(post("/api/login").content(reuquestBody).contentType(MediaType.APPLICATION_JSON));
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        String jwtToken = resultActions.andReturn().getResponse().getHeader(JwtVO.HEADER);
        System.out.println("테스트 : " + responseBody);
        System.out.println("테스트 : " + jwtToken);

        // then
        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
        Assertions.assertNotNull(jwtToken);
        assertTrue(jwtToken.startsWith(JwtVO.TOKEN_PREFIX));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.data.username").value("ssar"));
    }

    @Test
    public void unsuccessfulAuthentication_test() throws Exception {
        // given

        // when

        // then
    }
}
