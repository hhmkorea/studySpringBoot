package shop.mtcoding.bank.temp;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * packageName    : shop.mtcoding.bank.temp
 * fileName       : LongTest
 * author         : dotdot
 * date           : 2024-03-31
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-03-31        dotdot       최초 생성
 */
public class LongTest {

    @Test
    public void long_test3() throws Exception {
        // given
        Long v1 = 128L;
        Long v2 = 128L;

        // when

        // then
        assertThat(v1).isEqualTo(v2);
    }

    @Test
    public void long_test2() throws Exception {
        // given (2의 8승 - 256 범위 (-126 + 127)까지는 비교가 됨), 그 이상일 경우 longValue를 붙여야함.
        Long v1 = 128L;
        Long v2 = 128L;
        // when
        if (v1  == v2) {
            System.out.println("테스트 : v1이 동일합니다.");
        } else {
            System.out.println("테스트 : 동일하지 않습니다.");
        }
        // then
    }

    @Test
    public void long_test() throws Exception {
        // given
        Long number1 = 1111L;
        Long number2 = 1111L;

        // when
        if (number1.longValue() == number2.longValue()) {
            System.out.println("테스트 : 동일합니다.");
        } else {
            System.out.println("테스트 : 동일하지 않습니다.");
        }

        Long amount1 = 100L;
        Long amount2 = 1000L;

        if (amount1 < amount2) {
            System.out.println("테스트 : amount1이 작습니다.");
        } else {
            System.out.println("테스트 : amount1이 큽니다.");
        }
        // then


    }
}
