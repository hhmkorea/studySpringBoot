package shop.mtcoding.bank.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import shop.mtcoding.bank.domain.user.UserEnum;
import shop.mtcoding.bank.dto.ResponseDto;
import shop.mtcoding.bank.util.CustomResponseUtil;

// @Slf4j // JUnit테스트할때 문제 생겨서 Logger 사용
@Configuration // IoC에 설정파일로 등록해줌
public class SecurityConfig {
    public static final Logger log = LoggerFactory.getLogger(SecurityConfig.class);

    @Bean // IoC 컨테이너에 BCryptPasswordEncoder() 객체가 등록됨, @Configuration가 붙어있는 곳에서만 작동함.
    public BCryptPasswordEncoder passwordEncoder() {
        log.debug("디버그 : BCryptPasswordEncoder 빈 등록됨");
        return new BCryptPasswordEncoder();
    }

    // JWT 필터 등록이 필요함!

    // JWT 서버를 만들 예정!! Session 사용안함.
    // Spring Security 6.1.0부터는 메서드 체이닝의 사용을 지양하고 람다식을 통해 함수형으로 설정하게 지향함.
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        log.debug("디버그 : filterChain 빈 등록됨");
        http
                .headers((headerConfig) -> headerConfig.frameOptions(frameOptionsConfig -> frameOptionsConfig.disable() )) // iframe 허용안함.
                .csrf((csrfConfig) -> csrfConfig.disable()) // enable이면 post맨 작동안함 ( 참고: 유튜브 시큐리티 강의 )
                .cors((corsConfig-> corsConfig.configurationSource(configurationSource()))) // JS 요청되는거 허용여부 - 안함
                // jSessionId를 서버쪽에서 관리안하겠다는 뜻!!
                .sessionManagement((sessionManagement) -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // react, 앱으로 요청예정, HTML formLogin 방식 사용 안함.
                .formLogin((formLogin)->formLogin.disable())

                // Excepiton 가로채기
                .exceptionHandling(httpSecurityExceptionHandlingConfigurer -> httpSecurityExceptionHandlingConfigurer.authenticationEntryPoint((request, response, authException) -> {
                    CustomResponseUtil.unAuthentication(response, "로그인을 진행해 주세요.");
                }))

                // httpBasic은 브라우저가 팝업창을 이용해서 사용자 인증을 진행한다. - 비허용!!
                .httpBasic((httpSecurityHttpBasicConfigurer) -> httpSecurityHttpBasicConfigurer.disable())
                .authorizeHttpRequests((authorizationManagerRequestMatcherRegistry) ->
                        authorizationManagerRequestMatcherRegistry
                                .requestMatchers("/api/s/**").authenticated() // antMatchers 대신 requestMatchers 사용.
                                .requestMatchers("/api/admin/**").hasRole(""+ UserEnum.ADMIN) // 최근 공식문서에서는 ROLE_ 안붙여도 됨.
                                .anyRequest().permitAll()
                );

        return http.build();
    }

    public CorsConfigurationSource configurationSource() {
        log.debug("디버그 : configurationSource cors 설정이 SecurityFilterChain에 등록됨");
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*"); // GET, POST, PUT, DELETE (JS 요청 허용)
        configuration.addAllowedOriginPattern("*"); // 모든 IP 주소 허용 (frontend IP만 허용)
        configuration.setAllowCredentials(true); // 클라이언트에서 쿠키 요청 허용

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
