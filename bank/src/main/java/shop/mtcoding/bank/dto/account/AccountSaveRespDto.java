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

    public AccountSaveRespDto(Account account) { // Entity객체를 DTO로 옮김. Entity 객체를 DTO로 옮김. 원하는 것만 Lazy Loading을 위한 작업
        this.id = account.getId();
        this.number = account.getNumber();
        this.balance = account.getBalance();
    }
}
