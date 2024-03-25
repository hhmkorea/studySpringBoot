package shop.mtcoding.bank.config.jwt;

import org.junit.jupiter.api.Test;
import shop.mtcoding.bank.config.auth.LoginUser;
import shop.mtcoding.bank.domain.user.User;
import shop.mtcoding.bank.domain.user.UserEnum;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.assertj.core.api.Assertions.assertThat;

public class JwtProcessTest {
    @Test
    public void create_test() throws Exception {
        // given
        User user = User.builder().id(1L).role(UserEnum.CUSTOMER).build();
        LoginUser loginUser = new LoginUser(user); // id, role만 들어가 있음.

        // when
        String jwtToken = JwtProcess.create(loginUser);
        System.out.println("테스트 : "+jwtToken); // 값은 계속 바뀜.

        // then
        assertTrue(jwtToken.startsWith(JwtVO.TOKEN_PREFIX));
    }

    @Test
    public void verify_test() throws Exception {
        // given
        String jwtToken = "eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJiYW5rIiwiZXhwIjoxNzExMzg5NTY1LCJpZCI6MSwicm9sZSI6IkNVU1RPTUVSIn0.7JOmbzmDZE6RdFd1JRVpEF8H0pLBIMsvLowpBVm1Tzt-D6YNghzhn5we65ujjBYVMtrKWO4DENLBNpkH_tcMaQ";

        // when
        LoginUser loginUser = JwtProcess.verify(jwtToken);
        System.out.println("테스트 :"+loginUser.getUser().getId());
        System.out.println("테스트 :"+loginUser.getUser().getRole().name());

        // then
        assertThat(loginUser.getUser().getId()).isEqualTo(1L);
        assertThat(loginUser.getUser().getRole()).isEqualTo(UserEnum.ADMIN);
    }
}
