package shop.mtcoding.bank.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.mtcoding.bank.domain.account.Account;
import shop.mtcoding.bank.domain.account.AccountRepository;
import shop.mtcoding.bank.domain.transaction.Transaction;
import shop.mtcoding.bank.domain.transaction.TransactionEnum;
import shop.mtcoding.bank.domain.transaction.TransactionRepository;
import shop.mtcoding.bank.domain.user.User;
import shop.mtcoding.bank.domain.user.UserRepository;
import shop.mtcoding.bank.dto.account.AccountReqDto;
import shop.mtcoding.bank.dto.account.AccountReqDto.AccountDepositReqDto;
import shop.mtcoding.bank.dto.account.AccountReqDto.AccountTransferReqDto;
import shop.mtcoding.bank.dto.account.AccountReqDto.AccountWithdrawReqDto;
import shop.mtcoding.bank.dto.account.AccountRespDto.*;
import shop.mtcoding.bank.handler.ex.CustomApiException;

import java.util.List;
import java.util.Optional;

/**
 * packageName    : shop.mtcoding.bank.service
 * fileName       : AccountService
 * author         : dotdot
 * date           : 2024-03-27
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-03-27        dotdot       최초 생성
 */
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class AccountService {
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public AccountListRespDto findListByUser(Long userId) {
        User userPS = userRepository.findById(userId).orElseThrow(
                () -> new CustomApiException("유저를 찾을 수 없습니다.")
        );
        // 유저의 모든 계좌목록
        List<Account> accountListPS = accountRepository.findByUser_id(userId);
        return new AccountListRespDto(userPS, accountListPS);
    }

    @Transactional
    public AccountSaveRespDto saveAccount(AccountReqDto accountReqDto, Long userId) { // 계좌등록
        // User가 DB에 있는지 검증 겸 유저 엔티티 가져오기
        User userPS = userRepository.findById(userId).orElseThrow(
                () -> new CustomApiException("유저를 찾을 수 없습니다.")
        );

        // 해당 계좌가 DB에 있는지 중복여부 체크
        Optional<Account> accountOP = accountRepository.findByNumber(accountReqDto.getNumber());
        if (accountOP.isPresent()) {
            throw new CustomApiException("해당 계좌가 이미 존재합니다.");
        }

        // 계좌 등록
        Account accountPS = accountRepository.save(accountReqDto.toEntity(userPS));

        // DTO 응답
        return new AccountSaveRespDto(accountPS);
    }

    @Transactional
    public void deleteAccount(Long number, Long userId) {
        // 1. 계좌 확인
        Account accountPS = accountRepository.findByNumber(number).orElseThrow(
                () -> new CustomApiException("계좌를 찾을 수 없습니다.")
        );

        // 2. 계좌 소유자 확인
        accountPS.checkOwner(userId);

        // 3. 계좌 삭제
        accountRepository.deleteById(accountPS.getId());
    }

    @Transactional
    public AccountWithdrawRespDto withdrawAccount(AccountWithdrawReqDto accountWithdrawReqDto, Long userId) { // 계좌출금

        // 0원 체크
        if (accountWithdrawReqDto.getAmount() <= 0L) {
            throw new CustomApiException("0원 이하의 금액을 입금할 수 없습니다.");
        }

        // 출금계좌 확인
        Account withdrawAccountPS = accountRepository.findByNumber(accountWithdrawReqDto.getNumber())
                .orElseThrow(
                        () -> new CustomApiException("계좌를 찾을 수 없습니다.")
                );
        // 출금 소유자 확인 (로그인한 사람과 동일한지)
        withdrawAccountPS.checkOwner(userId);
        // 출금계좌 비밀번호 확인
        withdrawAccountPS.checkSamePassword(accountWithdrawReqDto.getPassword());
        // 출금계좌 잔액 확인
        withdrawAccountPS.checkBalance(accountWithdrawReqDto.getAmount());
        // 출금하기
        withdrawAccountPS.withdraw(accountWithdrawReqDto.getAmount());

        // 거래내역 남기기(내 계좌에서 ATM으로 출금)
        Transaction transaction = Transaction.builder()
                .withdrawAccount(withdrawAccountPS)
                .depositAccount(null) // 출금할때 입금 계좌는 필요없음.
                .withdrawAccountBalance(withdrawAccountPS.getBalance()) // 출금계좌 잔액!!
                .depositAccountBalance(null) //
                .ammount(accountWithdrawReqDto.getAmount())
                .gubun(TransactionEnum.WITHDRAW) // 출금
                .sender(accountWithdrawReqDto.getNumber() + "")
                .receiver("ATM") // ATM 입금
                .build();

        // DTO 응납
        Transaction transactionPS = transactionRepository.save(transaction);
        return new AccountWithdrawRespDto(withdrawAccountPS, transactionPS);
    }

    // 인증이 필요 없다.
    @Transactional
    public AccountDepositRespDto depositAccount(AccountDepositReqDto accountDepositReqDto) { // 계좌입금 : ATM -> 누군가의 계좌
        // 0원 체크
        if (accountDepositReqDto.getAmount() <= 0L) {
            throw new CustomApiException("0원 이하의 금액을 입금할 수 없습니다.");
        }

        // 입금계좌 확인
        Account depositAccountPS = accountRepository.findByNumber(accountDepositReqDto.getNumber())
                .orElseThrow(
                        () -> new CustomApiException("계좌를 찾을 수 없습니다.")
                );

        // 입금 (해당 계좌 balance 조정 - update문 - Durty Checking)
        depositAccountPS.deposit(accountDepositReqDto.getAmount());

        //System.out.println("테스트 : ssarAccount1 잔액 = " + depositAccountPS.getBalance());
        // 거래내역 남기기
        Transaction transaction = Transaction.builder()
                .depositAccount(depositAccountPS)
                .withdrawAccount(null) // 입금할때 출금 계좌는 필요없음.
                .depositAccountBalance(depositAccountPS.getBalance()) // 입금계좌 잔액!!
                .ammount(accountDepositReqDto.getAmount())
                .gubun(TransactionEnum.DEPOSIT) // 입금
                .sender("ATM") // ATM 출금
                .receiver(accountDepositReqDto.getNumber() + "")
                .tel(accountDepositReqDto.getTel())
                .build();

        Transaction transactionPS = transactionRepository.save(transaction);
        return new AccountDepositRespDto(depositAccountPS, transactionPS);
    }

    @Transactional
    public AccountTransferRespDto transferAccount(AccountTransferReqDto accountTransferReqDto, Long userId) { // 계좌이체

        // 출금 계좌와 입금 계좌가 동일하면 안됨
        if (accountTransferReqDto.getWithdrawNumber().longValue() == accountTransferReqDto.getDepositNumber().longValue()) {
            throw new CustomApiException("입출금 계좌가 동일합니다. ");
        }

        // 0원 체크
        if (accountTransferReqDto.getAmount() <= 0L) {
            throw new CustomApiException("0원 이하의 금액을 입금할 수 없습니다.");
        }

        // 출금계좌 확인
        Account withdrawAccountPS = accountRepository.findByNumber(accountTransferReqDto.getWithdrawNumber())
                .orElseThrow(
                        () -> new CustomApiException("출금계좌를 찾을 수 없습니다.")
                );

        // 입금계좌 확인
        Account depositAccountPS = accountRepository.findByNumber(accountTransferReqDto.getDepositNumber())
                .orElseThrow(
                        () -> new CustomApiException("입금계좌를 찾을 수 없습니다.")
                );

        // 출금 소유자 확인 (로그인한 사람과 동일한지)
        withdrawAccountPS.checkOwner(userId);

        // 출금계좌 비밀번호 확인
        withdrawAccountPS.checkSamePassword(accountTransferReqDto.getWithdrawPassword());

        // 출금계좌 잔액 확인
        withdrawAccountPS.checkBalance(accountTransferReqDto.getAmount());

        // 이체하기 : 출금해서 입금하기
        withdrawAccountPS.withdraw(accountTransferReqDto.getAmount());
        depositAccountPS.deposit(accountTransferReqDto.getAmount());

        // 거래내역 남기기(내 계좌에서 ATM으로 출금)
        Transaction transaction = Transaction.builder()
                .withdrawAccount(withdrawAccountPS)
                .depositAccount(depositAccountPS)
                .withdrawAccountBalance(withdrawAccountPS.getBalance()) // 출금 계좌 잔액
                .depositAccountBalance(depositAccountPS.getBalance()) // 입금 계좌 잔액
                .ammount(accountTransferReqDto.getAmount())
                .gubun(TransactionEnum.TRANSFER) // 이체
                .sender(accountTransferReqDto.getWithdrawNumber() + "") // 보내는 곳 : 출금계좌
                .receiver(accountTransferReqDto.getDepositNumber()+ "") // 받는 곳 : 입금계좌
                .build();

        // DTO 응납
        Transaction transactionPS = transactionRepository.save(transaction);
        return new AccountTransferRespDto(withdrawAccountPS, transactionPS);
    }

    public AccountDetailRespDto viewAcountDetail(Long number, Long userId, Integer page ) { // 계좌 상세보기
        // 1. 구분값 고정
        String gubun = "ALL";

        // 2. 계좌 확인
        Account accountPS = accountRepository.findByNumber(number).orElseThrow(
                () -> new CustomApiException("계좌를 찾을 수 없습니다.")
        );

        // 2. 계좌 소유자 확인
        accountPS.checkOwner(userId);

        // 4. 입출금 목록 보기
        List<Transaction> transactionListPS = transactionRepository.findTransactionList(accountPS.getId(), gubun, page);

        return new AccountDetailRespDto(accountPS, transactionListPS);
    }
}
