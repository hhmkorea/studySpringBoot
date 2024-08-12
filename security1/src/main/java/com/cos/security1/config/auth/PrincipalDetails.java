package com.cos.security1.config.auth;

import com.cos.security1.model.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/**
 * packageName    : com.cos.security1.config.auth
 * fileName       : PrincipalDetails
 * author         : dotdot
 * date           : 2024-06-02
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-06-02        dotdot       최초 생성
 */

// 시큐리티가 /login 주소 요청이 오면 낚아채서 로그인 진행시킴.
// 로그인 진행이 완료되면 시큐리티 session을 만들어줌.(Security ContextHolder)
// 오브젝트 타입 => Authentication 타입객체
// Authentication 안에 User 정보가 있어야 함.
// User 오브젝트 타입 => User Details 타입 객체

// Security Session => Authentication => UserDetails(PrincipalDetails)

@Data
public class PrincipalDetails implements UserDetails, OAuth2User {

    private User user; // Composition
    private Map<String, Object> attributes;

    // Constructor : 일반 로그인
    public PrincipalDetails(User user) {
        this.user = user;
    }

    // Constructor : OAuth 로그인
    public PrincipalDetails(User user, Map<String, Object> attributes) {
        this.user = user;
        this.attributes = attributes;
    }

    // 해당 User의 권한을 리턴하는 곳!!
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add(new GrantedAuthority() { // String 타입 객체를 담아 리턴하기 위해 넣음.
            @Override
            public String getAuthority() {
                return user.getRole();
            }
        });
        return collect;
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
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        // false? 우리 사이트에 1년동안 회원이 로그인을 안하면 휴먼 계정으로 하기로 함!
        // 현재시간 - 로그인시간 => 1년을 초과하면 return을 false로 바꿈.

        return true;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public String getName() {
        return null;
    }
}
