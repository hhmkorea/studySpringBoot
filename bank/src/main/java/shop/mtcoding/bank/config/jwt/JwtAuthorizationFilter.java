package shop.mtcoding.bank.config.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import shop.mtcoding.bank.config.auth.LoginUser;

import java.io.IOException;

/**
 * packageName    : shop.mtcoding.bank.config.jwt
 * fileName       : JwtAuthorizationFilter
 * author         : dotdot
 * date           : 2024-03-25
 * description    : 토큰 검증, 모든 주소에서 동작함.
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-03-25        dotdot       최초 생성
 */
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    private static final Logger log = LoggerFactory.getLogger(JwtAuthorizationFilter.class);

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    // JWT 토큰 헤더를 추가하지 않아도 해당 필터는 통과는 할 수 있지만, 결국 시큐리티단에서 세션 값 검증에 실패함.
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (isHeaderVerify(request, response)) {
            // 토큰이 존재함.
            log.debug("디버그 : 토큰이 존재함");
            String token = request.getHeader(JwtVO.HEADER).replace(JwtVO.TOKEN_PREFIX,"");
            // 토큰 검증 완료.
            log.debug("디버그 : 토큰이 검증이 완료됨");
            LoginUser loginUser = JwtProcess.verify(token);

            // 임시 세션 (UserDetails 타입 or username) : 여기는 id와 role만 들어가므로 객체 통채로 넣음. role만 잘들어가 있으면 됨!!
            Authentication authentication = new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities()); // 객체, 패스워드 모름, 권한
            SecurityContextHolder.getContext().setAuthentication(authentication); // 강제 로그인!!!
            log.debug("디버그 : 임시 세션이 생성됨");

        }
        chain.doFilter(request, response);
    }

    // 토큰 유효성검사
    private boolean isHeaderVerify(HttpServletRequest request, HttpServletResponse response) {
        String header = request.getHeader(JwtVO.HEADER);
        if (header == null || header.startsWith(JwtVO.TOKEN_PREFIX)) {
            return false;
        } else {
            return true;
        }
    }
}
