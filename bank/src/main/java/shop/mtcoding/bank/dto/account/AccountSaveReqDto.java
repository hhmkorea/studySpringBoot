package shop.mtcoding.bank.dto.account;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
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
public class AccountSaveReqDto {
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

