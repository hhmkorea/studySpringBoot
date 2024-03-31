package shop.mtcoding.bank.dto.account;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import shop.mtcoding.bank.domain.account.Account;
import shop.mtcoding.bank.domain.user.User;

/**
 * packageName    : shop.mtcoding.bank.dto.account
 * fileName       : AccountSaveReqDto
 * author         : dotdot
 * date           : 2024-03-28
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-03-28        dotdot       최초 생성
 */
@Setter
@Getter
public class AccountReqDto {

    @Setter
    @Getter
    public static class AccountTransferReqDto { // 요청 DTO
        @NotNull
        @Digits(integer = 4, fraction = 4)
        private Long WithdrawNumber; // 출금 계좌 번호
        @NotNull
        @Digits(integer = 4, fraction = 4)
        private Long dipositNumber; // 입금 계좌 번호
        @NotNull
        @Digits(integer = 4, fraction = 4)
        private Long withdrawPassword; // 출금 계좌 비밀번호
        @NotNull
        private Long amount;
        @Pattern(regexp = "TRANSFER")
        private String gubun;
    }

    @Setter
    @Getter
    public static class AccountWithdrawReqDto { // 요청 DTO
        @NotNull
        @Digits(integer = 4, fraction = 4)
        private Long number;
        @NotNull
        @Digits(integer = 4, fraction = 4)
        private Long password;
        @NotNull
        private Long amount;
        @Pattern(regexp = "WITHDRAW")
        private String gubun;
    }

    @Getter
    @Setter
    public static class AccountDepositReqDto { // 입금 요청 DTO
        @NotNull
        @Digits(integer = 4, fraction = 4)
        private Long number;
        @NotNull
        private Long amount; // 0원 유효성 검사
        @NotEmpty
        @Pattern(regexp = "DEPOSIT")
        private String gubun; // DEPOSIT
        @NotEmpty
        @Pattern(regexp = "^[0-9]{11}")
        private String tel;
    }

    @NotNull
    @Digits(integer = 4, fraction = 4) // Long 길이 체크
    private Long number;
    @NotNull
    @Digits(integer = 4, fraction = 4) // Long 길이 체크
    private Long password;

    public Account toEntity(User user) {
        return Account.builder()
                .number(number)
                .password(password)
                .balance(1000L)
                .user(user)
                .build();
    }
}

