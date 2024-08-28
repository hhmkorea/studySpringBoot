package com.cos.jwt.config.jwt;

/**
 * packageName    : com.cos.jwt.config.jwt
 * fileName       : JwtProperties
 * author         : dotdot
 * date           : 2024-08-28
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-08-28        dotdot       최초 생성
 */
public interface JwtProperties {
    String SECRET = "cos"; // 우리 서버만 알고 있는 비밀값
    int EXPIRATION_TIME = 60000*10; // 10분 (1/1000초)
    String TOKEN_PREFIX = "Bearer ";
    String HEADER_STRING = "Authorization";
}
