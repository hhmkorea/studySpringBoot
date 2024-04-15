package com.cos.blog.contorller;

import com.cos.blog.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @Autowired
    private BoardService boardService;

    // 컨트롤러에서 세션을 어떻게 찾는가?
    // @AuthenticationPrincipal PrincipalDetail principal
    @GetMapping({"","/"})
    public String index(Model model) {
        model.addAttribute("boards", boardService.viewList());
        return "index"; // viewResolver 작동!!
    }

    // USER 권한이 필요
    @GetMapping("/board/saveForm")
    public String saveForm() {
        return "board/saveForm";
    }
}
