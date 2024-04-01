package shop.mtcoding.bank.domain.transaction;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import shop.mtcoding.bank.config.dummy.DummyObject;
import shop.mtcoding.bank.domain.account.Account;
import shop.mtcoding.bank.domain.account.AccountRepository;
import shop.mtcoding.bank.domain.user.User;
import shop.mtcoding.bank.domain.user.UserRepository;

import java.util.List;

@ActiveProfiles("test")
@DataJpaTest // DB 관련된 Bean이 다 올라온다.
/**
 * packageName    : shop.mtcoding.bank.domain.transaction
 * fileName       : TransactionRepositoryImplTest
 * author         : dotdot
 * date           : 2024-04-01
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-04-01        dotdot       최초 생성
 */
public class TransactionRepositoryImplTest  extends DummyObject {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private EntityManager em;

    @BeforeEach
    public void setUp() {
        autoIncrementReset();
        dataSetting();
    }

    @Test
    public void findTransactionList_all_test() throws Exception {
        // given
        Long accountId = 1L;

        // when
        List<Transaction> transactionListPS = transactionRepository.findTransactionList(accountId, "ALL", 0);
        transactionListPS.forEach((t) -> {
            System.out.println("Id : " + t.getId());
            System.out.println("금액 : " + t.getAmmount());
            System.out.println("보낸사람 : " + t.getSender());
            System.out.println("받는사람 : " + t.getReceiver());
            System.out.println("출금계좌 잔액 : " + t.getWithdrawAccountBalance());
            System.out.println("입금계좌 잔액 : " + t.getDepositAccountBalance());
            System.out.println("findTransactionList_all_test ================================ ");
        });

        // then
    }

    @Test
    public void dataJpa_test1() {
        List<Transaction> transactionList = transactionRepository.findAll();
        transactionList.forEach((transaction -> {
            System.out.println("Id : " + transaction.getId());
            System.out.println("Sender : " + transaction.getSender());
            System.out.println("Receiver : " + transaction.getReceiver());
            System.out.println("Gubun : " + transaction.getGubun());
            System.out.println("dataJpa_test1 ================================ ");
        }));
    }

    @Test
    public void dataJpa_test2() {
        List<Transaction> transactionList = transactionRepository.findAll();
        transactionList.forEach((transaction -> {
            System.out.println("Id : " + transaction.getId());
            System.out.println("Sender : " + transaction.getSender());
            System.out.println("Receiver : " + transaction.getReceiver());
            System.out.println("Gubun : " + transaction.getGubun());
            System.out.println("dataJpa_test2 ================================ ");
        }));
    }

    private void dataSetting() {
        User ssar = userRepository.save(newUser("ssar", "쌀"));
        User cos = userRepository.save(newUser("cos", "코스,"));
        User love = userRepository.save(newUser("love", "러브"));
        User admin = userRepository.save(newUser("admin", "관리자"));

        Account ssarAccount1 = accountRepository.save(newAccount(1111L, ssar)); // 1000원 갖고 있음.
        Account cosAccount = accountRepository.save(newAccount(2222L, cos));
        Account loveAccount = accountRepository.save(newAccount(3333L, love));
        Account ssarAccount2 = accountRepository.save(newAccount(4444L, ssar));

        Transaction withdrawTransaction1 = transactionRepository
                .save(newWithdrawTransaction(ssarAccount1, accountRepository));
        Transaction depositTransaction1 = transactionRepository
                .save(newDepositTransaction(cosAccount, accountRepository));
        Transaction transferTransaction1 = transactionRepository
                .save(newTransferTransaction(ssarAccount1, cosAccount, accountRepository));
        Transaction transferTransaction2 = transactionRepository
                .save(newTransferTransaction(ssarAccount1, loveAccount, accountRepository));
        Transaction transferTransaction3 = transactionRepository
                .save(newTransferTransaction(cosAccount, ssarAccount1, accountRepository));
    }

    private void autoIncrementReset() {
        em.createNativeQuery("ALTER TABLE user_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE account_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE transaction_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
    }

}
