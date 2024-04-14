package com.cos.blog.test;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * packageName    : com.cos.blog.test
 * fileName       : EncTest
 * author         : dotdot
 * date           : 2024-04-14
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-04-14        dotdot       최초 생성
 */
public class EncTest {
    @Test
    public void encodingHash_test() throws Exception {
        String encPassword = new BCryptPasswordEncoder().encode("1234");
        System.out.println("1234 해쉬 : " + encPassword);
    }
}
