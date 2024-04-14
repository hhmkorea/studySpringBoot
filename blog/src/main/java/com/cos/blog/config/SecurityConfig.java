package com.cos.blog.config;

/**
 * packageName    : com.cos.blog.config
 * fileName       : SecurityConfig
 * author         : dotdot
 * date           : 2024-04-13
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-04-13        dotdot       최초 생성
 */

import com.cos.blog.config.auth.PrincipalDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// 빈 등록 : 스프링 컨테이너에서 객체를 관리할 수 있게 하는 것
@Configuration // 빈등록 (IoC관리)
@ComponentScan
public class SecurityConfig implements WebMvcConfigurer {

    @Autowired
    private PrincipalDetailService principalDetailService;

    @Bean
    BCryptPasswordEncoder encodePWD() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")  // 모든 리소스 허용
                .addResourceLocations("classpath:/templates/") // 리소스 위치 설정
                .setCacheControl(CacheControl.noCache());
    }

    @Bean
    SecurityFilterChain configure(HttpSecurity http) throws Exception {
        System.out.println("디버그 : filterChain 빈 등록됨");

        http.headers(h -> h.frameOptions(f -> f.sameOrigin())); // sameOrigin

        // 사이트 위변조 요청 방지
        http.csrf(c -> c.disable()); // csrf 토큰 비활성화 (테스트시 걸어두는 게 좋음)// enable이면 post맨 작동안함 ( 참고: 유튜브 시큐리티 강의 )
        http.cors(co -> co.configurationSource(configurationSource())); // JS 요청되는거 허용여부 - 안함
        // jSessionId를 서버쪽에서 관리안하겠다는 뜻!!
        http.sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // 인가(접근권한) 설정
        http.authorizeHttpRequests(auth ->
                auth.requestMatchers("/", "/auth/**", "/WEB-INF/**", "/js/**", "/css/**", "/image/**")
                        .permitAll()
                        .anyRequest().authenticated());

        // 로그인 설정
        http.formLogin(f -> f.loginPage("/auth/loginForm") // 인증이 되지 않은 요청은 무조건 loginForm 으로 옴.
                .loginProcessingUrl("/auth/loginProc")
                .defaultSuccessUrl("/")); // 스프링 시큐리티가 해당 주소로 로그인을 가로채서 대신 로그인 해준다.

        /* 시큐리티가 대신 로그인해주면서 password를 가로치기 하는데
           해당 password가 무엇으로 해쉬가 되어 회원가입이 되었는지 알아야
           같은 해쉬로 암호화해서 DB에 있는 해쉬랑 비교할 수 있음.  */
        // 사용자 인증 처리 컴포넌트 서비스 등록
        // http.userDetailsService(principalDetailService); // TO_DO : 일단 주석처리

        return http.build();
    }

    public CorsConfigurationSource configurationSource() {
        System.out.println("디버그 : configurationSource cors 설정이 SecurityFilterChain에 등록됨");
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*"); // GET, POST, PUT, DELETE (JS 요청 허용)
        configuration.addAllowedOriginPattern("*"); // 모든 IP 주소 허용 (frontend IP만 허용 react)
        configuration.setAllowCredentials(true); // 클라이언트에서 쿠키 요청 허용
        configuration.addExposedHeader("Authorization"); // 옛날에는 디폴트, 지금은 아님. Cors 정책이 바뀜.

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
