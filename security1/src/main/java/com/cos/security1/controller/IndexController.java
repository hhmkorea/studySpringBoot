package com.cos.security1.controller;

import com.cos.security1.model.User;
import com.cos.security1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * packageName    : com.cos.security1.controller
 * fileName       : IndexController
 * author         : dotdot
 * date           : 2024-05-31
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-05-31        dotdot       최초 생성
 */

@Controller // View를 리턴하겠다!!
public class IndexController {

    @Autowired
    private UserRepository userRepository;

    //localhost:8080/
    //localhost:8080
    @GetMapping({"","/"})
    public String index() {
        // mustache 기본폴더 src/main/resources/
        // ViewResolver 설정 : templates (prefix), .mustache(suffix) pom.xml로 의존성 삽입하면 생략가능.
        return "index"; // src/main/resources/templates/index.mustache
    }

    @GetMapping("/user")
    public @ResponseBody String user() { // @ResponseBody : text와 json 데이터 뿌려줌.
        return "user";
    }

    @GetMapping("/admin")
    public @ResponseBody String admin() {
        return "admin";
    }

    @GetMapping("/manager")
    public @ResponseBody String manager() {
        return "manager";
    }

    // 스프링 시큐리티가 해당 주소 낚아채감. - SecurityConfig 작성후 작동안함.
    @GetMapping("/loginForm") // 로그인 화면
    public String loginForm() {
        return "loginForm";
    }

    @GetMapping("/joinForm") // 회원가입 화면
    public String joinForm() {
        return "joinForm";
    }

    @PostMapping("/join") // 회원가입 진행
    public @ResponseBody String join(User user) {
        System.out.println(user);
        user.setRole("ROLE_USER");

        userRepository.save(user); // 회원가입 잘됨. 비밀번호 : 1234 => 시큐리티로 로그인 할 수 없음. 이유는 패스워드가 암호회가 안되었기 때문.
        return "join";
    }
}
