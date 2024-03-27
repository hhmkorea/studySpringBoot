package shop.mtcoding.bank.domain.account;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    // JPA query method
    // select * from account where number = :number
    // 리펙토링
    Optional<Account> findByNumber(Long number);

}
