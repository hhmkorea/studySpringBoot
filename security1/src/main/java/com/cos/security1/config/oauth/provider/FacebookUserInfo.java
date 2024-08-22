package com.cos.security1.config.oauth.provider;

import java.util.Map;

/**
 * packageName    : com.cos.security1.config.oauth.provider
 * fileName       : GoogleUserInfo
 * author         : dotdot
 * date           : 2024-08-14
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-08-14        dotdot       최초 생성
 */
public class FacebookUserInfo implements OAuth2UserInfo {

    private Map<String, Object> attributes; // oAuth2User.getAttributes()

    public FacebookUserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String getProviderId() {
        return (String) attributes.get("id");
    }

    @Override
    public String getProvider() {
        return "facebook";
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }

    @Override
    public String getName() {
        return (String) attributes.get("name");
    }
}