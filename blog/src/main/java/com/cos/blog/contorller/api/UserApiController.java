package com.cos.blog.contorller.api;

import com.cos.blog.contorller.dto.ResponseDto;
import com.cos.blog.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * packageName    : com.cos.blog.contorller.api
 * fileName       : UserApiController
 * author         : dotdot
 * date           : 2024-04-10
 * description    : 회원가입 Ajax 요청 Controller(앱에서도 사용가능)
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-04-10        dotdot       최초 생성
 */
@RestController
public class UserApiController {

    @PostMapping("/api/user")
    public ResponseDto<Integer> save(@RequestBody User user) {
        System.out.println("UserApiController : save 호출됨");
        // 실제로 DB에 insert를 하고 아래에서 return 하면 됨.
        return new ResponseDto<Integer>(HttpStatus.OK, 1); // 자바 오브젝트를 json으로 변환해서 리턴(Jackson)
    }
}
