package com.cos.blog.service;

import com.cos.blog.model.Board;
import com.cos.blog.model.User;
import com.cos.blog.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    public void write(Board board, User user) { // 글쓰기(title, content)
        board.setCount(0); // 조회수
        board.setUser(user);
        boardRepository.save(board);
    }

    public List<Board> viewList() { // 글목록 보기
        return boardRepository.findAll();
    }
}
