package shop.mtcoding.bank.dto.account;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import shop.mtcoding.bank.domain.account.Account;
import shop.mtcoding.bank.domain.transaction.Transaction;
import shop.mtcoding.bank.domain.user.User;
import shop.mtcoding.bank.util.CustomDateUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * packageName    : shop.mtcoding.bank.dto.account
 * fileName       : AccountSaveRespDto
 * author         : dotdot
 * date           : 2024-03-28
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-03-28        dotdot       최초 생성
 */
public class AccountRespDto {

    @Setter
    @Getter
    public static class AccountTransferRespDto { // 입금요청 응답 DTO
        private Long id; // 계좌ID
        private Long number; // 계좌번호
        private Long balance; // 출금 계좌 잔액
        private TransactionDto transaction; // 트랜잭션 로그(잔액은 못보고, 히스토리만 남김)

        public AccountTransferRespDto(Account account, Transaction transaction) {
            this.id = account.getId();
            this.number = account.getNumber();
            this.balance = account.getBalance();
            this.transaction = new TransactionDto(transaction);
        }

        @Setter
        @Getter
        public class TransactionDto {
            private Long id;
            private String gubun;
            private String sender;
            private String reciver;
            private Long amount;
            //@JsonIgnore
            private Long depositAccountBalance; // 입금계좌 잔액
            private String createdAt;

            public TransactionDto(Transaction transaction) {
                this.id = transaction.getId();
                this.gubun = transaction.getGubun().getValue();
                this.sender = transaction.getSender();
                this.reciver = transaction.getReceiver();
                this.amount = transaction.getAmmount();
                this.depositAccountBalance = transaction.getDepositAccountBalance();
                this.createdAt = CustomDateUtil.toStringFormat(transaction.getCreatedAt());
            }
        }
    }

    // DTO가 똑같아도 재사용하지 않기 (나중에 만약에 출금할때 DTO가 달라져야 하면 DTO를 공유하면 수정잘못하면 망함 - 독립적으로 만드세요)
    @Setter
    @Getter
    public static class AccountWithdrawRespDto { // 입금요청 응답 DTO
        private Long id; // 계좌ID
        private Long number; // 계좌번호
        private Long balance; // 잔액
        private TransactionDto transaction; // 트랜잭션 로그(잔액은 못보고, 히스토리만 남김)

        public AccountWithdrawRespDto(Account account, Transaction transaction) {
            this.id = account.getId();
            this.number = account.getNumber();
            this.balance = account.getBalance();
            this.transaction = new TransactionDto(transaction);
        }

        @Setter
        @Getter
        public class TransactionDto {
            private Long id;
            private String gubun;
            private String sender;
            private String reciver;
            private Long amount;
            private String createdAt;

            public TransactionDto(Transaction transaction) {
                this.id = transaction.getId();
                this.gubun = transaction.getGubun().getValue();
                this.sender = transaction.getSender();
                this.reciver = transaction.getReceiver();
                this.amount = transaction.getAmmount();
                this.createdAt = CustomDateUtil.toStringFormat(transaction.getCreatedAt());
            }
        }
    }

    @Setter
    @Getter
    public static class AccountDepositRespDto { // 입금요청 응답 DTO
        private Long id; // 계좌ID
        private Long number; // 계좌번호
        private TransactionDto transaction; // 트랜잭션 로그(잔액은 못보고, 히스토리만 남김)

        public AccountDepositRespDto(Account account, Transaction transaction) { // 생성자 만들고 Account, Transaction 객체로 변경해서서 설정하기. 객체로 받게끔 수정.
            this.id = account.getId();
            this.number = account.getNumber();
            this.transaction = new TransactionDto(transaction); // Transaction 안에 있는 값을 DTO로 바꿔서 넣어줌.
        }

        @Setter
        @Getter
        public class TransactionDto {
            private Long id;
            private String gubun;
            private String sender;
            private String reciver;
            private Long amount;
            private String tel;
            private String createdAt;
            @JsonIgnore
            private Long depositAccountBalance; // 클라이언트에게 전달X -> 서비스 단에서 테스트 용도

            public TransactionDto(Transaction transaction) { // 생성자 만들고 transaction 객체로 변경해서서 설정하기. 객체로 받게끔 수정.
                this.id = transaction.getId();
                this.gubun = transaction.getGubun().getValue();
                this.sender = transaction.getSender();
                this.reciver = transaction.getReceiver();
                this.amount = transaction.getAmmount();
                this.depositAccountBalance = transaction.getDepositAccountBalance();
                this.tel = transaction.getTel();
                this.createdAt = CustomDateUtil.toStringFormat(transaction.getCreatedAt());
            }
        }
    }

    @Setter
    @Getter
    public static class AccountSaveRespDto {
        private Long id;
        private Long number;
        private Long balance;

        public AccountSaveRespDto(Account account) { // Entity객체를 DTO로 옮김. Entity 객체를 DTO로 옮김. 원하는 것만 Lazy Loading을 위한 작업
            this.id = account.getId();
            this.number = account.getNumber();
            this.balance = account.getBalance();
        }
    }

    @Setter
    @Getter
    public static class AccountListRespDto {
        private String fullname;
        private List<AccountDto> accounts = new ArrayList<>();

        public AccountListRespDto(User user, List<Account> accounts) {
            this.fullname = user.getFullname();
            //this.accounts = accounts.stream().map((account -> new AccountDto(account)).collect(Collectors.toList());
            this.accounts = accounts.stream().map(AccountDto::new).collect(Collectors.toList());
            // [account, account]

        }

        public class AccountDto {
            private Long id;
            private Long number;
            private Long balance;

            public AccountDto(Account account) { // Entity 객체를 DTO로 옮김. 원하는 것만 Lazy Loading을 위한 작업
                this.id = account.getId();
                this.number = account.getNumber();
                this.balance = account.getBalance();
            }
        }
    }

}
