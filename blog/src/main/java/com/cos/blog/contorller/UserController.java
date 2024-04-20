package com.cos.blog.contorller;

import com.cos.blog.model.KakaoProfile;
import com.cos.blog.model.OAuthToken;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;


/**
 * packageName    : com.cos.blog.contorller
 * fileName       : UserController
 * author         : dotdot
 * date           : 2024-04-09
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-04-09        dotdot       최초 생성
 */

// 인증이 안된 사용자들이 출입할 수 있는 경로를 /auth/** 허용
// 그냥 주소가 / 이면 index.jsp 허용
// static 이하에 있는 /js/**, /css/**, /image/** 허용

@Controller
public class UserController {

    @GetMapping("/auth/joinForm")
    public String joinForm() {
        return "user/joinForm";
    }

    @GetMapping("/auth/loginForm")
    public String loginForm() {
        return "user/loginForm";
    }

    @GetMapping("/auth/kakao/callback")
    public @ResponseBody String kakaoCallback(String code) { // @ResponseBody : Data를 리턴해주는 컨트롤러 함수

        // POST 방식으로 key=value 데이터를 요청(카카오쪽으로)
        /* 데이터 요청 객체 지원해주는 라이브러리
        Retrofit2 : 안드로이드
        OkHttp
        RestTemplate */

        RestTemplate rt = new RestTemplate();

        // HttpHeader 오브젝트 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");

        // HttpBody 오브젝트 생성
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", "66c53837ec775df5a3d2f7c75cdc8e0b");
        params.add("redirect_uri", "http://localhost:8000/auth/kakao/callback");
        params.add("code", code);

        // HttpHeader와 HttpBody를 하나의 오브젝트에 담기
        HttpEntity<MultiValueMap<String,String>> kakaoTokenRequest = new HttpEntity<>(params, headers);

        // Http 요청하기 - Post방식으로 - 그리고 response 변수의 응답 받음.
        ResponseEntity <String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token", // 토큰 발급 요청 주소
                HttpMethod.POST, // 요청 메서드 타입
                kakaoTokenRequest, // 전달할 데이터 : header, body
                String.class // 응답받을 타입
        );

        /* 토큰 담는 객체 지원 라이브러리 : Gson, Json Simple, ObjectMapper */
        ObjectMapper objectMapper = new ObjectMapper();
        OAuthToken oauthToken = null;
        try {
            oauthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        System.out.println("카카오 엑세스 토큰:" + oauthToken.getAccess_token());

        RestTemplate rt2 = new RestTemplate();

        HttpHeaders headers2 = new HttpHeaders();
        headers2.add("Authorization", "Bearer "+oauthToken.getAccess_token());
        headers2.add("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
        headers2.add("property_keys", "kakao_account.email");

        HttpEntity<MultiValueMap<String,String>> kakaoProfileRequest2 = new HttpEntity<>(headers2);

        ResponseEntity <String> response2 = rt2.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoProfileRequest2,
                String.class // 응답받을 타입
        );

        ObjectMapper objectMapper2 = new ObjectMapper();
        KakaoProfile kakaoProfile = null;
        try {
            kakaoProfile = objectMapper2.readValue(response2.getBody(), KakaoProfile.class);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        // User 오브젝트 : username, password, email
        System.out.println("카카오 아이디(번호) : " + kakaoProfile.getId());
        System.out.println("카카오 계정(이메일) : " + kakaoProfile.getKakao_account().getEmail());



        return response2.getBody();
    }

    @GetMapping("/user/updateForm")
    public String updateForm() {
        return "user/updateForm";
    }
}
