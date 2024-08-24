package com.cos.jwt.controller;

import com.cos.jwt.model.User;
import com.cos.jwt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

/**
 * packageName    : com.cos.jwt.controller
 * fileName       : RestApiController
 * author         : dotdot
 * date           : 2024-08-21
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-08-21        dotdot       최초 생성
 */

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
//@CrossOrigin // CORS 허용 인증이 필요하지 않은 요청만 허용.
public class RestApiController {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("home") // 모든 사람이 접근 가능.
    public String home() {
        return "<h1>home</h1>";
    }

//    @PostMapping("token") // JWT 임시 토큰 테스트
//    public String token() {
//        return "<h1>token</h1>";
//    }

    @PostMapping("join")
    public String join(@RequestBody User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles("USER"); // 권한은 기본으로 USER로 설정합니다. ---> security 최신 버전에서는 권한 적용시 ROLE_ 쓰지 않음.
        userRepository.save(user);
        return "회원가입완료";
    }
}
