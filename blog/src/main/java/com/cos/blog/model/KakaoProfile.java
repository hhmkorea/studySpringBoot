package com.cos.blog.model;

import lombok.Data;

@Data
public class KakaoProfile {
    Long id;
    public String connected_at;
    public Properties properties;
    public KakaoAccount kakao_account;

    @Data
    public class Properties {
        public String nickname;

    }

    @Data
    public class KakaoAccount {
        public Boolean profile_nickname_needs_agreement;
        public Profile profile;

        @Data
        class Profile {
            public String nickname;
            public Boolean is_default_nickname;

        }

    }
}


