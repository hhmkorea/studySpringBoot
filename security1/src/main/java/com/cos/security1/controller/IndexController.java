package com.cos.security1.controller;

import com.cos.security1.config.auth.PrincipalDetails;
import com.cos.security1.model.User;
import com.cos.security1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
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

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/test/login") // 일반적인 로그인.
    public @ResponseBody String testLogin(Authentication authentication, @AuthenticationPrincipal PrincipalDetails userDetails) {
        // Authentication, DI(의존성주입), PrincipalDetails 타입(=UserDetails)
        // @AuthenticationPrincipal : security 세션정보 접근 가능한 annotation.
        System.out.println("/test/login ================");
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        System.out.println("principalDetails : " + principalDetails.getUser());
        System.out.println("userDetails : " + userDetails.getUsername());
        return "세션 정보 확인하기";
    }

    @GetMapping("/test/oauth/login") // Oauth2 로그인.
    public @ResponseBody String testOauthLogin(Authentication authentication, @AuthenticationPrincipal OAuth2User oauth) {
        // Authentication, DI(의존성주입), OAuth2User 타입
        // @AuthenticationPrincipal : security 세션정보 접근 가능한 annotation.
        System.out.println("/test/oauth/login ================");
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal(); // OAuth2User로 다운 캐스팅
        System.out.println("oAuth2User.getAttributes : " + oAuth2User.getAttributes());
        System.out.println("OAuth2User : " + oauth.getAttributes());

        return "Oauth2 세션 정보 확인하기";
    }

    //localhost:8080/
    //localhost:8080
    @GetMapping({"","/"})
    public String index() {
        // mustache 기본폴더 src/main/resources/
        // ViewResolver 설정 : templates (prefix), .mustache(suffix) pom.xml로 의존성 삽입하면 생략가능.
        return "index"; // src/main/resources/templates/index.mustache
    }

    @GetMapping("/user")
    public @ResponseBody String user(@AuthenticationPrincipal PrincipalDetails principalDetails) { // @ResponseBody : text와 json 데이터 뿌려줌.
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
    public String join(User user) {
        System.out.println(user);
        user.setRole("USER"); // ROLE_USER는 옛날방식. 에러남.
        String rawPassword = user.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
        user.setPassword(encPassword);
        userRepository.save(user); // 회원가입 잘됨. 비밀번호 : 1234 => 시큐리티로 로그인 할 수 없음. 이유는 패스워드가 암호회가 안되었기 때문.
        return "redirect:/loginForm"; // redirect: 뒤에 붙은 함수를 호출해줌.
    }

    //@Secured("ADMIN") // ADMIN 권한만 접근가능 --- 에러?
    @GetMapping("/info")
    public @ResponseBody String info() {
        return "개인정보";
    }

    //@PreAuthorize("hasAnyRole('MANAGER','ADMIN')") --- 에러?
    @GetMapping("/data")
    public @ResponseBody String data() {
        return "데이터정보";
    }

}
