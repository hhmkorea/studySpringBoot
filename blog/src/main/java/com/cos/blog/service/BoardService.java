package com.cos.blog.service;

import com.cos.blog.model.Board;
import com.cos.blog.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * packageName    : com.cos.blog.service
 * fileName       : UserService
 * author         : dotdot
 * date           : 2024-04-11
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-04-11        dotdot       최초 생성
 */

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @Transactional
    public void write(Board board) { // 글쓰기(title, content)
        board.setCount(0);
        boardRepository.save(board);
    }

}
