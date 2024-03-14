package shop.mtcoding.demo;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class IndexControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void index_test() throws Exception {
        ResultActions resultActions = mvc.perform(MockMvcRequestBuilders.get("/index"));

        resultActions.andExpect(MockMvcResultMatchers.status().isCreated());

        Assertions.assertThat("기대하는값").isEqualTo("기대하는값 아님");
    }
}
