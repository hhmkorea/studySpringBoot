package com.cos.jwt.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * packageName    : com.cos.jwt.controller
 * fileName       : RestApiController
 * author         : dotdot
 * date           : 2024-08-21
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-08-21        dotdot       최초 생성
 */
@RestController
public class RestApiController {

    @GetMapping("home")
    public String home() {
        return "<h1>home</h1>";
    }
}
