package com.cos.blog.test;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * packageName    : com.cos.blog.test
 * fileName       : DummyControllerTest
 * author         : dotdot
 * date           : 2024-04-07
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-04-07        dotdot       최초 생성
 */
@RestController
public class DummyControllerTest {

    @Autowired // 의존성 주입(DI), class가 메모리에 뜰때 @Autowired로 지정된 변수도 같이 뜸.
    private UserRepository userRepository;

    // {id} 주소로 parameter 전달 받을 수 있음.
    // http://localhost:8000/blog/dummy/user/5
    @GetMapping("/dummy/user/{id}")
    public User detail(@PathVariable int id) {
        /* Optional<User>
            user/4 찾으면 DB에서 못찾아오게 되면 user가 null 이 됨.
            그럼 return null 이 되어서 프로그램 문제 생김.
            Optional로 User 객체를 가져올테니 null인지 아닌지 판단해서 return 해야함.
            .get() : null이 절대 없으면 바로 뽑아줌.
            .orElseGet() : null이면 객체 하나 만들어서 넣어주기.
            .orElseThrow() : null이면 Exception으로 던져서 처리.
        */

        // 람다식, Lamda Expression
        User user = userRepository.findById(id).orElseThrow(() -> {
            return new IllegalArgumentException("해당 사용자는 없습니다. id: " + id);
        } );
        return user;
    }

    // http://localhost:8000/blog/dummy/join (요청)
    // http의 body에 username, password, email 데이터를 가지고 (요청)
    @PostMapping("/dummy/join")
    public String join(User user) { // key=value (약속된 규칙)
        System.out.println("id: " + user.getId());
        System.out.println("username: " + user.getUsername());
        System.out.println("password: " + user.getPassword());
        System.out.println("email: " + user.getEmail());
        System.out.println("role: " + user.getRole());
        System.out.println("createDate: " + user.getCreateDate());

        user.setRole(RoleType.USER);
        userRepository.save(user);
        return "회원가입이 완료되었습니다.";
    }
}
