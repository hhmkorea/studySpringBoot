package shop.mtcoding.bank.config.dummy;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import shop.mtcoding.bank.domain.account.Account;
import shop.mtcoding.bank.domain.transaction.Transaction;
import shop.mtcoding.bank.domain.transaction.TransactionEnum;
import shop.mtcoding.bank.domain.user.User;
import shop.mtcoding.bank.domain.user.UserEnum;

import java.time.LocalDateTime;

public class DummyObject {

    // 계좌 1111L 1000원
    // 입금 트랜잭션 -> 계좌 1100원 변경 -> 입금 트랜잭션 히스토리가 생성되어야 함.
    protected static Transaction newMockDepositTransaction(Long id, Account account) {
        account.deposit(100L);

        Transaction transaction = Transaction.builder()
                .id(id)
                .withdrawAccount(null) // 입금할때 출금 계좌는 필요없음.
                .depositAccount(account)
                .withdrawAccountBalance(null)
                .depositAccountBalance(account.getBalance()) // 영속화된 잔액!!
                .ammount(100L)
                .gubun(TransactionEnum.DEPOSIT) // 입금
                .sender("ATM") // ATM 출금
                .receiver(account.getNumber()+"")
                .tel("10188887777")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        return transaction;
    }

    protected static User newUser(String username, String fullname) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encPassword = passwordEncoder.encode("1234");

        return User.builder()
                .username(username)
                .password(encPassword)
                .email(username+"@nate.com")
                .fullname(fullname)
                .role(UserEnum.CUSTOMER)
                .build();
    }

    protected User newMockUser(Long id, String username, String fullname) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encPassword = passwordEncoder.encode("1234");

        return User.builder()
                .id(id)
                .username(username)
                .password(encPassword)
                .email(username+"@nate.com")
                .fullname(fullname)
                .role(UserEnum.CUSTOMER)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    protected Account newAccount(Long number, User user) {
        return Account.builder()
                .number(number)
                .password(1234L)
                .balance(1000L)
                .user(user)
                .build();
    }

    protected Account newMockAccount(Long id, Long number, Long balance, User user) {
        return Account.builder()
                .id(id)
                .number(number)
                .password(1234L)
                .balance(balance)
                .user(user)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
