package com.cos.blog.handler;

import com.cos.blog.contorller.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

/**
 * packageName    : com.cos.blog.handler
 * fileName       : GlobalExceptionHandler
 * author         : dotdot
 * date           : 2024-04-08
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-04-08        dotdot       최초 생성
 */

@ControllerAdvice // 어디서든 Exception 발생하면 불러들이게 지정함.
@RestController
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ResponseDto<String> handleArgumentException(Exception e) {
        return new ResponseDto<String>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()); // 500
    }
}
