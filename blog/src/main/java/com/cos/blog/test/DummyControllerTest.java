package com.cos.blog.test;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
// html 파일이 아니라 data를 리턴해주는 controller = RestController
@RestController
public class DummyControllerTest {

    @Autowired // 의존성 주입(DI), class가 메모리에 뜰때 @Autowired로 지정된 변수도 같이 뜸.
    private UserRepository userRepository;

    /* .save(id)
        id를 전달하지 않으면 insert,
        id에 대한 데이터가 없으면 insert,
        id에 대한 데이터가 있으면 update
     */
    // email, password
    @Transactional // 함수 종료시에 자동 commit이 됨.
    @PutMapping("/dummy/user/{id}")
    public User updateUser(@PathVariable int id, @RequestBody User requestUser) {
        // @RequestBody : json 데이타 받을때 필요함.
        // json 데이터를 요청 => Java Object(MessageConverter의 Jackson 라이브러리가 변환해서 받아줌).
        System.out.println("id :" + id);
        System.out.println("password : " + requestUser.getPassword());
        System.out.println("email : " + requestUser.getEmail());

        // save로 업데이트 할경우 해당 객체의 값을 조회해서 넣어야 함,
        User user = userRepository.findById(id).orElseThrow(() -> {
            return new IllegalArgumentException("수정에 실패하였습니다.");
        }); // 영속화된 User 오브젝트
        user.setPassword(requestUser.getPassword());
        user.setEmail(requestUser.getEmail());

        //userRepository.save(user);
        return user;
    }

    @GetMapping("/dummy/users")
    public List<User> list() {
        return userRepository.findAll();
    }

    // 한 페이지당 2건에 데이터를 리턴받아 볼 예정
    @GetMapping("/dummy/user")
    public List<User> pageList(@PageableDefault(size = 2, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<User> pagingUser =  userRepository.findAll(pageable);

        if (pagingUser.isLast()) {

        }
        List<User> users = pagingUser.getContent();

        return users;
    }

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

        /* 요청 : 웹브라우저, 리턴 : user 객체 = 자바오브젝트
        --> 변환 ( 웹 브라우저가 이해할 수 있는 데이터 ) -> json (Gson 라이브러리)
            스프링부트 = MessageConverter라는 애가 응답시에 자동 작동
            만약에 자바 오브젝트를 리턴하게 되면 MessageConverter가 Jackson 라이브러리 호출해서
            user 오브젝트를 json으로 변환해서 브라우저에 던져줌.
         */
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
