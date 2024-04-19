package com.cos.blog.model;

import lombok.Data;

/**
 * packageName    : com.cos.blog.model
 * fileName       : OAuthToken
 * author         : dotdot
 * date           : 2024-04-19
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-04-19        dotdot       최초 생성
 */

@Data
public class OAuthToken {
    private String access_token;
    private String token_type;
    private String refresh_token;
    private int expires_in;
    private String scope;
    private int refresh_token_expires_in;
}
