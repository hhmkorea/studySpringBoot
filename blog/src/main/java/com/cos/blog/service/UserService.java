package com.cos.blog.service;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * packageName    : com.cos.blog.service
 * fileName       : UserService
 * author         : dotdot
 * date           : 2024-04-11
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-04-11        dotdot       최초 생성
 */
// 스프링이 컴포넌트 스캔을 통해서 Bean에 등록을 해줌. IoC를 해준다.
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired // DI가 되어 주입됨.
    private BCryptPasswordEncoder encoder;

    @Transactional(readOnly = true)
    public User findMember(String username) { // 회원찾기 : 기존에 가입된 회원인지 아닌지 체크.
        User user = userRepository.findAllByUsername(username).orElseGet(() -> {
            return new User();
        });
        return user;
    }

    @Transactional
    public int joinMember(User user) { // 회원가입(정상:1, 비정상:-1)
        String rawPassword = user.getPassword(); // 1234 원문
        String encPassword = encoder.encode(rawPassword); // 해쉬
        user.setPassword(encPassword);
        user.setRole(RoleType.USER);
        try {
            userRepository.save(user);
            return 1;
        } catch (Exception e) {
            return -1;
        }
    }

    @Transactional
    public void updateMember(User user) { // 회원수정
        // 수정시에는 영속성 컨텍스트 User 오브젝트를 영속화 시키고, 영속화된 User 오브젝트를 수정.
        // select를 해서 User 오브젝트를 DB로부터 가져오는 이유는 영속화 하기 위해서
        // 영속화된 오브젝트를 변경하면 자동으로 DB에 update문을 날려줌.
        User userPS = userRepository.findById(user.getId()).orElseThrow(() -> {
            return new IllegalArgumentException("회원찾기 실패");
        });

        // Validation 체크 => oauth 필드에 값이 없으면 수정가능.
        if(userPS.getOauth() == null || userPS.getOauth().equals("")) {
            String rawPassword = user.getPassword();
            String encPassword = encoder.encode(rawPassword);
            if(!rawPassword.isEmpty()) {
                userPS.setPassword(encPassword);
            }
            userPS.setEmail(user.getEmail());
        }
        // 회원수정 함수 종료시 = 서비스 종료 = 트랜잭션 종료 = commit 이 자동으로 됩니다.
        // 영속화된 userPS 객체 변화가 감지되면 더티체킹이 되어 update문을 날려줌.
    }

}
