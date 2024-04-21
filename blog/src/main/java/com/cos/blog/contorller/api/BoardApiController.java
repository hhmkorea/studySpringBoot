package com.cos.blog.contorller.api;

import com.cos.blog.config.auth.PrincipalDetail;
import com.cos.blog.contorller.dto.ReplySaveRequestDto;
import com.cos.blog.contorller.dto.ResponseDto;
import com.cos.blog.model.Board;
import com.cos.blog.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

/**
 * packageName    : com.cos.blog.contorller.api
 * fileName       : UserApiController
 * author         : dotdot
 * date           : 2024-04-10
 * description    : 회원가입 Ajax 요청 Controller(앱에서도 사용가능)
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-04-10        dotdot       최초 생성
 */
@RestController
public class BoardApiController {

    @Autowired
    private BoardService boardService;

    @PostMapping("/api/board")
    public ResponseDto<Integer> save(@RequestBody Board board, @AuthenticationPrincipal PrincipalDetail principal) {
        boardService.write(board, principal.getUser()); // 글쓰기
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

    @DeleteMapping("/api/board/{id}")
    public ResponseDto<Integer> deleteById(@PathVariable int id) {
        boardService.deleteById(id);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

    @PutMapping("/api/board/{id}")
    public ResponseDto<Integer> update(@PathVariable int id, @RequestBody Board board) {
        boardService.update(id, board);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

    // 데이터 받을 때 컨트롤러에서 dto를 만들어서 받는게 좋다.
    // dto를 사용하지 않은 이유는!!
    @PostMapping("/api/board/{boardId}/reply")
    public ResponseDto<Integer> replySave(@RequestBody ReplySaveRequestDto replySaveRequestDto) {
        boardService.writeReply(replySaveRequestDto); // 댓글쓰기
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

    @DeleteMapping("/api/board/{boardId}/reply/{replyId}")
    public ResponseDto<Integer> replyDelete(@PathVariable int replyId) {
        boardService.deleteReply(replyId);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

}
