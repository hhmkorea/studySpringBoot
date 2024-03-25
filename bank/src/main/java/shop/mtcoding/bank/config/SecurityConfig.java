package shop.mtcoding.bank.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import shop.mtcoding.bank.config.jwt.JwtAuthenticationFilter;
import shop.mtcoding.bank.config.jwt.JwtAuthorizationFilter;
import shop.mtcoding.bank.domain.user.UserEnum;
import shop.mtcoding.bank.util.CustomResponseUtil;

// @Slf4j // JUnit테스트할때 문제 생겨서 Logger 사용
@Configuration // IoC에 설정파일로 등록해줌
public class SecurityConfig {
    public final Logger log = LoggerFactory.getLogger(SecurityConfig.class);

    @Bean // IoC 컨테이너에 BCryptPasswordEncoder() 객체가 등록됨, @Configuration가 붙어있는 곳에서만 작동함.
    public BCryptPasswordEncoder passwordEncoder() {
        log.debug("디버그 : BCryptPasswordEncoder 빈 등록됨");
        return new BCryptPasswordEncoder();
    }

    // JWT 필터 등록이 필요함!
    public class CustomSecurityFileterManager extends AbstractHttpConfigurer<CustomSecurityFileterManager, HttpSecurity> {
        @Override
        public void configure(HttpSecurity builder) throws Exception {
            AuthenticationManager authenticationManager = builder.getSharedObject(AuthenticationManager.class); // 인증 매니저 접근이 가능함.
            builder.addFilter(new JwtAuthenticationFilter(authenticationManager));
            builder.addFilter(new JwtAuthorizationFilter(authenticationManager));
            super.configure(builder);
        }

        public HttpSecurity build() {
            return getBuilder();
        }
    }

    // JWT 서버를 만들 예정!! Session 사용안함.
    // Spring Security 6.1.0부터는 메서드 체이닝의 사용을 지양하고 람다식을 통해 함수형으로 설정하게 지향함.
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        log.debug("디버그 : filterChain 빈 등록됨");

        http.headers(h -> h.frameOptions(f -> f.sameOrigin())); // sameOrigin
        http.csrf(cf -> cf.disable()); // enable이면 post맨 작동안함 ( 참고: 유튜브 시큐리티 강의 )
        http.cors(co -> co.configurationSource(configurationSource())); // JS 요청되는거 허용여부 - 안함
        // jSessionId를 서버쪽에서 관리안하겠다는 뜻!!
        http.sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        // react, 앱으로 요청예정, HTML formLogin 방식 사용 안함.
        http.formLogin(f -> f.disable());

        // httpBasic은 브라우저가 팝업창을 이용해서 사용자 인증을 진행한다. - 비허용!!
        http.httpBasic(hb -> hb.disable());

        // jwt필터 등록
        http.with(new CustomSecurityFileterManager(), c -> c.build());

        // 인증 실패
        http.exceptionHandling(e -> e.authenticationEntryPoint((request, response, authException) -> {
            CustomResponseUtil.fail(response, "로그인을 진행해 주세요.", HttpStatus.UNAUTHORIZED);
        }));
        http.exceptionHandling(e -> e.accessDeniedHandler((request, response, accessDeniedException) -> {
            CustomResponseUtil.fail(response, "권한이 없습니다.", HttpStatus.FORBIDDEN);
        }));

        //.apply(new CustomSecurityFileterManager()) // apply() API 더이상 지원안함.
        http.authorizeHttpRequests(c ->
                c.requestMatchers("/api/s/**").authenticated() // antMatchers 대신 requestMatchers 사용.
                        .requestMatchers("/api/admin/**").hasRole("" + UserEnum.ADMIN) // 최근 공식문서에서는 ROLE_ 안붙여도 됨.
                        .anyRequest().permitAll()
        );

        return http.build();
    }

    public CorsConfigurationSource configurationSource() {
        log.debug("디버그 : configurationSource cors 설정이 SecurityFilterChain에 등록됨");
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*"); // GET, POST, PUT, DELETE (JS 요청 허용)
        configuration.addAllowedOriginPattern("*"); // 모든 IP 주소 허용 (frontend IP만 허용 react)
        configuration.setAllowCredentials(true); // 클라이언트에서 쿠키 요청 허용
        configuration.addExposedHeader("Authorization"); // 옛날에는 디폴트, 지금은 아님.

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
