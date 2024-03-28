package shop.mtcoding.bank.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import shop.mtcoding.bank.config.dummy.DummyObject;
import shop.mtcoding.bank.domain.account.Account;
import shop.mtcoding.bank.domain.account.AccountRepository;
import shop.mtcoding.bank.domain.user.User;
import shop.mtcoding.bank.domain.user.UserRepository;
import shop.mtcoding.bank.dto.account.AccountSaveReqDto;
import shop.mtcoding.bank.dto.account.AccountSaveRespDto;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

/**
 * packageName    : shop.mtcoding.bank.service
 * fileName       : AccountServiceTest
 * author         : dotdot
 * date           : 2024-03-28
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-03-28        dotdot       최초 생성
 */
@ExtendWith(MockitoExtension.class)
public class AccountServiceTest extends DummyObject {

    @InjectMocks // 모든 Mock들이 InectMocks 로 주입됨
    private AccountService accountService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AccountRepository accountRepository;

    @Spy // 진짜 객체를 InectMocks 에 주입한다.
    private ObjectMapper om;

    @Test
    public void saveAccount_test() throws Exception {
        // given
        Long userId = 1L;
        AccountSaveReqDto accountSaveReqDto = new AccountSaveReqDto();
        accountSaveReqDto.setNumber(1111L);
        accountSaveReqDto.setPassword(1234L);

        // stub 1
        User ssar = newMockUser(userId, "ssar", "쌀");
        Mockito.when(userRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.of(ssar));

        // stub 2
        Mockito.when(accountRepository.findByNumber(ArgumentMatchers.any())).thenReturn(Optional.empty());

        // stub 3
        Account ssarAccount = newMockAccount(1L, 1111L, 1000L, ssar);
        Mockito.when(accountRepository.save(ArgumentMatchers.any())).thenReturn(ssarAccount);

        // when
        AccountSaveRespDto accountSaveRespDto = accountService.saveAccount(accountSaveReqDto, userId);
        String responseBody = om.writeValueAsString(accountSaveRespDto);
        System.out.println("테스트 : "+responseBody);

        // then
        assertThat(accountSaveRespDto.getNumber()).isEqualTo(1111L);
    }
}
