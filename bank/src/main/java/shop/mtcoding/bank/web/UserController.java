package shop.mtcoding.bank.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import shop.mtcoding.bank.dto.ResponseDto;
import shop.mtcoding.bank.dto.user.UserReqDto.*;
import shop.mtcoding.bank.dto.user.UserRespDto.*;
import shop.mtcoding.bank.service.UserService;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
/* @RestController : @Controller에 @ResponseBody가 추가된것
 Restful(Representational State Transfer) 웹서비스 컨트롤러,
 최근 Data 응답으로 제공하는 REST API를 개발할때 주로 사용(Controller는 View 반환 목적),
 HTTP Response Body가 생성되어 응답할 수 있음(ttpStatus.OK 등등...) */
public class UserController {
    private final UserService userService;

    @PostMapping("/join") // 인증이 필요없는 주소
    public ResponseEntity<?> join(@RequestBody JoinReqDto joinReqDto) {
        // ResponseEntity : 컨트롤러를 통해 객체 반환할때 쓰임.
        // @RequestBody : Json타입으로 값을 받음.
        JoinRespDto joinRespDto = userService.joinMember(joinReqDto);
        return new ResponseEntity<>(new ResponseDto<>(1, "회원가입 성공", joinRespDto), HttpStatus.CREATED);
    }
}
