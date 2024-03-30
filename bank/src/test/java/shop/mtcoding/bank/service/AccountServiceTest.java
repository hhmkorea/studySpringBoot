package shop.mtcoding.bank.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import shop.mtcoding.bank.config.dummy.DummyObject;
import shop.mtcoding.bank.domain.account.Account;
import shop.mtcoding.bank.domain.account.AccountRepository;
import shop.mtcoding.bank.domain.transaction.Transaction;
import shop.mtcoding.bank.domain.transaction.TransactionRepository;
import shop.mtcoding.bank.domain.user.User;
import shop.mtcoding.bank.domain.user.UserRepository;
import shop.mtcoding.bank.dto.account.AccountReqDto;
import shop.mtcoding.bank.dto.account.AccountReqDto.AccountDepositReqDto;
import shop.mtcoding.bank.dto.account.AccountRespDto.AccountDepositRespDto;
import shop.mtcoding.bank.dto.account.AccountRespDto.AccountSaveRespDto;
import shop.mtcoding.bank.handler.ex.CustomApiException;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

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

    @Mock
    private TransactionRepository transactionRepository;

    @Spy // 진짜 객체를 InectMocks 에 주입한다.
    private ObjectMapper om;

    @Test
    public void saveAccount_test() throws Exception {
        // given
        Long userId = 1L;
        AccountReqDto accountReqDto = new AccountReqDto();
        accountReqDto.setNumber(1111L);
        accountReqDto.setPassword(1234L);

        // stub 1
        User ssar = newMockUser(userId, "ssar", "쌀");
        Mockito.when(userRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.of(ssar));

        // stub 2
        Mockito.when(accountRepository.findByNumber(ArgumentMatchers.any())).thenReturn(Optional.empty());

        // stub 3
        Account ssarAccount = newMockAccount(1L, 1111L, 1000L, ssar);
        Mockito.when(accountRepository.save(ArgumentMatchers.any())).thenReturn(ssarAccount);

        // when
        AccountSaveRespDto accountRespDto = accountService.saveAccount(accountReqDto, userId);
        String responseBody = om.writeValueAsString(accountRespDto);
        System.out.println("테스트 : "+responseBody);

        // then
        assertThat(accountRespDto.getNumber()).isEqualTo(1111L);
    }

    @Test
    public void deleteAccount_test() throws Exception {
        // given
        Long number = 1111L;
        Long userId = 2L;

        // stub
        User ssar = newMockUser(1L, "ssar", "쌀");
        Account ssarAccount = newMockAccount(1L, 1111L, 1000L, ssar);
        Mockito.when(accountRepository.findByNumber(ArgumentMatchers.any())).thenReturn(Optional.of(ssarAccount));

        // when
        accountService.deleteAccount(number, userId);

        // then
        Assertions.assertThrows(CustomApiException.class, () -> accountService.deleteAccount(number, userId));
    }

    // Account -> balance 변경됐는지
    // Transaction -> balance 잘 기록됐는지
    @Test
    public void dipositAccount_test() throws Exception {
        // given
        AccountDepositReqDto accountDepositReqDto = new AccountDepositReqDto();
        accountDepositReqDto.setNumber(1111L);
        accountDepositReqDto.setAmount(100L);
        accountDepositReqDto.setGubun("DEPOSIT");
        accountDepositReqDto.setTel("01088887777");

        // stub 1
        User ssar = newMockUser(1L, "ssar", "쌀"); // 실행됨
        Account ssarAccount1 = newMockAccount(1L, 1111L, 1000L, ssar); // 실행됨 - ssarAccount1 -> 1,000원
        Mockito.when(accountRepository.findByNumber(ArgumentMatchers.any())).thenReturn(Optional.of(ssarAccount1)); // 실행안됨 -> service 호출 후 실행됨 -> 1,100원

        // stub 2 (Stub 이 진행될때마다 연관된 객체는 새로 만들어서 주입하기 - 타이밍 때문에 꼬인다)
        Account ssarAccount2 = newMockAccount(1L, 1111L, 1000L, ssar);
        Transaction transaction = newMockDepositTransaction(1L, ssarAccount2);
        Mockito.when(transactionRepository.save(ArgumentMatchers.any())).thenReturn(transaction);

        // when
        AccountDepositRespDto accountDepositRespDto = accountService.depositAccount(accountDepositReqDto);
        System.out.println("테스트 : 트랜잭션 입금계좌 잔엑 = "+ accountDepositRespDto.getTransaction().getDepositAccountBalance());
        System.out.println("테스트 : 계좌쪽 잔액(ssarAccount1) = "+ ssarAccount1.getBalance());
        System.out.println("테스트 : 계좌쪽 잔액(ssarAccount2) = "+ ssarAccount2.getBalance());

        // then
        assertThat(ssarAccount1.getBalance()).isEqualTo(1100L);
        assertThat(accountDepositRespDto.getTransaction().getDepositAccountBalance()).isEqualTo(1100L);
    }

    // DTO가 잘 만들어 졌는지 확인.
    @Test
    public void dipositAccount2_test() throws Exception {
        // given
        AccountDepositReqDto accountDepositReqDto = new AccountDepositReqDto();
        accountDepositReqDto.setNumber(1111L);
        accountDepositReqDto.setAmount(100L);
        accountDepositReqDto.setGubun("DEPOSIT");
        accountDepositReqDto.setTel("01088887777");

        // stub 1
        User ssar1 = newMockUser(1L, "ssar", "쌀");
        Account ssarAccount1 = newMockAccount(1L, 1111L, 1000L, ssar1);
        Mockito.when(accountRepository.findByNumber(ArgumentMatchers.any())).thenReturn(Optional.of(ssarAccount1));

        // stub 2
        User ssar2 = newMockUser(1L, "ssar", "쌀");
        Account ssarAccount2 = newMockAccount(1L, 1111L, 1000L, ssar2);
        Transaction transaction = newMockDepositTransaction(1L, ssarAccount2);
        Mockito.when(transactionRepository.save(ArgumentMatchers.any())).thenReturn(transaction);

        // when
        AccountDepositRespDto accountDepositRespDto = accountService.depositAccount(accountDepositReqDto);
        String responseBody = om.writeValueAsString(accountDepositRespDto);
        System.out.println("테스트 : "+responseBody);

        // then
        assertThat(ssarAccount1.getBalance()).isEqualTo(1100L);
    }
}
