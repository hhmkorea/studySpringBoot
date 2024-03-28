package shop.mtcoding.bank.dto.account;

import lombok.Getter;
import lombok.Setter;
import shop.mtcoding.bank.domain.account.Account;

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
@Setter
@Getter
public class AccountSaveRespDto {
    private Long id;
    private Long number;
    private Long balance;

    public AccountSaveRespDto(Account account) {
        this.id = account.getId();
        this.number = account.getNumber();
        this.balance = account.getBalance();
    }
}
