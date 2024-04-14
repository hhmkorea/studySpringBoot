package com.cos.blog.config.auth;

import com.cos.blog.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

/**
 * packageName    : com.cos.blog.config.auth
 * fileName       : PrincipalDetail
 * author         : dotdot
 * date           : 2024-04-14
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-04-14        dotdot       최초 생성
 */

/* Spring Security가 로그인 요청을 가로채서 로그인을 진행하고 완료가 되면
   UserDetails 타입의 오브젝트를 Spring Security의 고유한 세션 저장소에 저장을 해준다. */

public class PrincipalDetail implements UserDetails {
    private User user; // Composition, 객체를 품고있음

    public PrincipalDetail(User user) {
        this.user = user;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }
    @Override
    public String getUsername() {
        return user.getUsername();
    }
    @Override
    public boolean isAccountNonExpired() { // 만료안된 계정?(true:만료안됨)
        return true;
    }
    @Override
    public boolean isAccountNonLocked() { // 잠기지 않은 계정?(true:잠기지 않음)
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() { // 비밀번호 만료안됨?(true:만료안됨)
        return true;
    }
    @Override
    public boolean isEnabled() { // 계정활성화?(true:계정활성화중)
        return true;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { // 계정이 갖고 있는 권한 목록 리턴
        Collection<GrantedAuthority> collectors = new ArrayList<>(); // ArrayList 부모에 Collection 있음.
        collectors.add(() -> { return "ROLE_" + user.getRole();}); // 람다식
        return collectors;
    }
}
