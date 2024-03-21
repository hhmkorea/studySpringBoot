package shop.mtcoding.bank.temp;

import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

public class RegexTest {
    @Test
    public void doKo_test() throws Exception { // 한글만 된다.
        String value = "한글";
        boolean result = Pattern.matches("^[가-힣]+$", value);
        System.out.println("테스트 : "+result);
    }
    @Test
    public void dontKo_test() throws Exception { // 한글만 안된다.
        String value = "abc";
        boolean result = Pattern.matches("^[^ㄱ-ㅎ가-힣]*$", value);
        System.out.println("테스트 : "+result);
    }
    @Test
    public void doEn_test() throws Exception { // 영어만 된다.
        String value = "ssar";
        boolean result = Pattern.matches("^[a-zA-Z]+$", value);
        System.out.println("테스트 : "+result);
    }
    @Test
    public void dontEn_test() throws Exception { // 영어만 안된다.
        String value = "가22";
        boolean result = Pattern.matches("^[^a-zA-Z]*$", value);
        System.out.println("테스트 : "+result);
    }
    @Test
    public void doEnNum_test() throws Exception { // 영어와 숫자만 된다.
        String value = "ab12";
        boolean result = Pattern.matches("^[a-zA-Z0-9]*$", value);
        System.out.println("테스트 : "+result);
    }
    @Test
    public void doEn_ChkLen_test() throws Exception { // 영어만되고 길이는 최소2, 최대4
        String value = "ss";
        boolean result = Pattern.matches("^[a-zA-Z]{2,4}$", value);
        System.out.println("테스트 : "+result);
    }

    // username, email, fullname
    @Test
    public void user_username_test() throws Exception {
        String username = "ssar";
        boolean result = Pattern.matches("^[a-zA-Z0-9]{2,20}$", username);
        System.out.println("테스트 : "+result);
    }
    @Test
    public void user_userfullname_test() throws Exception {
        String username = "쌀";
        boolean result = Pattern.matches("^[a-zA-Z가-힣]{1,20}$", username);
        System.out.println("테스트 : "+result);
    }
    @Test
    public void user_email_test() throws Exception {
        String username = "ssar@nate.com";
        boolean result = Pattern.matches("^[a-zA-Z0-9]{2,10}@[a-zA-Z0-9]{2,6}\\.[a-zA]{2,3}$", username);
        System.out.println("테스트 : "+result);
    }
}
