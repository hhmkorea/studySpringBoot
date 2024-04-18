package com.cos.blog.contorller.api;

import com.cos.blog.contorller.dto.ResponseDto;
import com.cos.blog.model.User;
import com.cos.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/auth/joinProc")
    public ResponseDto<Integer> save(@RequestBody User user) { // username, password, email
        System.out.println("UserApiController : save 호출됨");

        userService.joinMember(user); // 회원가입
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1); // 자바 오브젝트를 json으로 변환해서 리턴(Jackson)
    }

    @PutMapping("/user")
    public ResponseDto<Integer> update(@RequestBody User user) {
        // 기본적으로 key=value, x-www.form-urlencoded 형태로 데이타 들어옴.
        // @RequestBody가 있어야 json 타입으로 받을 수 있음.
        userService.updateMember(user); // 회원수정
        // 여기서는 트랜잭션이 종료 되기 때문에 DB 값은 변경 됨.
        // But! 세션값은 변경되지 않은 상태이기 때문에 화면에는 변경되지 않아보임. 직접 세션값을 변경처리 해야함.

        // 세션등록
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }
}
