package shop.mtcoding.bank.domain.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    // JPA query method
    // select * from account where number = :number
    // 신경안써도 됨 : 리펙토링 해야함!! (계좌 소유자 확인시에 쿼리가 두번 나가기 때문 join fetch) - account.getUser().getId()
    // join fetch를 하면 조인해서 Lazy Loading 하기 전에 객체에 값을 미리 가져올 수 있다.
    // @Query("SELECT ac FROM Account ac JOIN FETCH ac.user u WHERE ac.number = :number") // select 할때 미리 당겨옴. JPA 쿼리는 객체 지향으로 클래스 이름으로 테이블을 만듬.
    Optional<Account> findByNumber(@Param("number") Long number);

    // JPA query method
    // select * from account where user_id = :id
    List<Account> findByUser_id(Long id);
}
