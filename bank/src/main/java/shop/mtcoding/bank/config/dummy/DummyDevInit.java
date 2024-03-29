package shop.mtcoding.bank.config.dummy;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import shop.mtcoding.bank.domain.user.User;
import shop.mtcoding.bank.domain.user.UserRepository;

/**
 * packageName    : shop.mtcoding.bank.config.dummy
 * fileName       : DummyInit
 * author         : dotdot
 * date           : 2024-03-28
 * description    : dev 테스트용 유저 추가.
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-03-28        dotdot       최초 생성
 */
@Configuration
public class DummyDevInit extends DummyObject{

    @Profile("dev") // prod 모드에서는 실행되면 안된다.
    @Bean
    CommandLineRunner init(UserRepository userRepository) {
        return (args) -> {
            // 서버 실행시에 무조건 실행된다.
            User user = userRepository.save(newUser("ssar","쌀"));
        };
    }
}
