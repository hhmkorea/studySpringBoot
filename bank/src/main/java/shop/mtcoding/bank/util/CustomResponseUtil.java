package shop.mtcoding.bank.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import shop.mtcoding.bank.dto.ResponseDto;

public class CustomResponseUtil {
    public static final Logger log = LoggerFactory.getLogger(CustomResponseUtil.class);

    public static void success(HttpServletResponse response, Object dto) {
        try {
            ObjectMapper om = new ObjectMapper();
            ResponseDto<?> responseDto = new ResponseDto<>(1, "로그인 성공", dto);
            String responseBody = om.writeValueAsString(responseDto); // Object를 Json으로 변환
            response.setContentType("application/json; charset=utf-8"); // json 응답
            response.setStatus(200); // 200 : 인증 완료
            response.getWriter().println(responseBody); // 예쁘게 메시지를 포장하는 공통적인 응답 DTO를 만들어보자!!
        }catch (Exception e) {
            log.error("서버 파싱 에러");
        }
    }

    public static void unAuthentication(HttpServletResponse response, String msg) {
        try {
            ObjectMapper om = new ObjectMapper();
            ResponseDto<?> responseDto = new ResponseDto<>(-1, msg, null);
            String responseBody = om.writeValueAsString(responseDto); // Object를 Json으로 변환
            response.setContentType("application/json; charset=utf-8"); // json 응답
            response.setStatus(401); // 401 : 인증이 안된거, 403 : 권한이 없는거
            response.getWriter().println(responseBody); // 예쁘게 메시지를 포장하는 공통적인 응답 DTO를 만들어보자!!
        }catch (Exception e) {
            log.error("서버 파싱 에러");
        }
    }
}
