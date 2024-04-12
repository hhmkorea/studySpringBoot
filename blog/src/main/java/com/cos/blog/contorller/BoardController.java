package com.cos.blog.contorller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * packageName    : com.cos.blog.contorller
 * fileName       : BoardController
 * author         : dotdot
 * date           : 2024-04-09
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-04-09        dotdot       최초 생성
 */
@Controller
public class BoardController {

    @GetMapping({"","/"})
    public String index() {
        // /WEB-INF/views/index.jsp
        return "index";
    }
}