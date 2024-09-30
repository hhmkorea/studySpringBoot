package com.cos.blog.test;

import com.cos.blog.model.Reply;
import org.testng.annotations.Test;

/**
 * packageName    : com.cos.blog.test
 * fileName       : ReplyObjectTest
 * author         : dotdot
 * date           : 2024-04-21
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-04-21        dotdot       최초 생성
 */
public class ReplyObjectTest {

    @Test
    public void toStringTest() {
        Reply reply = Reply.builder()
                .id(1)
                .user(null)
                .board(null)
                .content("안녕")
                .build();
        System.out.println(reply); // 오브젝트 출력시에 toString()이 자동 출력됨.
    }
}
