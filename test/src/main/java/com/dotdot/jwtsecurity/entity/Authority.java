package com.dotdot.jwtsecurity.entity;

/**
 * packageName    : com.dotdot.jwtsecurity.domain
 * fileName       : Authority
 * author         : dotdot
 * date           : 2024-03-24
 * description    : 유저 권한 설정, Spring Security 자체에서 내부적으로 사용함. 정확한 형식을 갖춰야 함.
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-03-24        dotdot       최초 생성
 */
public enum Authority {
    ROLE_USER, ROLE_ADMIN
}
