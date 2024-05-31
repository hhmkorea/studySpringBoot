package com.cos.security1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * packageName    : com.cos.security1.controller
 * fileName       : IndexController
 * author         : dotdot
 * date           : 2024-05-31
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-05-31        dotdot       최초 생성
 */

@Controller // View를 리턴하겠다!!
public class IndexController {
    //localhost:8080/
    //localhost:8080
    @GetMapping({"","/"})
    public String index() {
        // mustache 기본폴더 src/main/resources/
        // ViewResolver 설정 : templates (prefix), .mustache(suffix) pom.xml로 의존성 삽입하면 생략가능.
        return "index"; // src/main/resources/templates/index.mustache
    }
}
