package com.cos.security1.config.oauth.provider;

/**
 * packageName    : com.cos.security1.config.oauth.provider
 * fileName       : OAuth2UserInfo
 * author         : dotdot
 * date           : 2024-08-14
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-08-14        dotdot       최초 생성
 */
public interface OAuth2UserInfo {
    String getProviderId();
    String getProvider();
    String getEmail();
    String getName();
}
