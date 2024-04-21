package com.cos.blog.service;

import com.cos.blog.contorller.dto.ReplySaveRequestDto;
import com.cos.blog.model.Board;
import com.cos.blog.model.Reply;
import com.cos.blog.model.User;
import com.cos.blog.repository.BoardRepository;
import com.cos.blog.repository.ReplyRepository;
import com.cos.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Autowired
    private ReplyRepository replyRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void write(Board board, User user) { // 글쓰기(title, content)
        board.setCount(0); // 조회수
        board.setUser(user);
        boardRepository.save(board);
    }

    @Transactional(readOnly = true)
    public Page<Board> viewList(Pageable pageable) { // 글목록 보기
        return boardRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Board viewDetail(int id) { // 글 상세보기
        return boardRepository.findById(id)
                .orElseThrow(() -> {
                    return new IllegalArgumentException("글 상세보기 실패 : 아이디를 찾을 수 없습니다.");
                }
        );
    }

    @Transactional
    public void deleteById(int id) { // 글 삭제하기
        System.out.println("deleteById : " + id);
        boardRepository.deleteById(id);
    }

    @Transactional
    public void update(int id, Board requestBoard) { // 글 수정하기
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> {
                    return new IllegalArgumentException("글 찾기 실패 : 아이디를 찾을 수 없습니다.");
                }); // 영속화 완료
        board.setTitle(requestBoard.getTitle());
        board.setContent(requestBoard.getContent());
        // 해담 함수로 종료시(Service가 종료될때) 트랜잭션이 종료됨.
        // 이때 더티체킹(자동 업데이트)가 됨. db flush
    }

    @Transactional
    public void writeReply(ReplySaveRequestDto replySaveRequestDto) { // 댓글쓰기

        User user = userRepository.findById(replySaveRequestDto.getUserId()).orElseThrow(() -> {
            return new IllegalArgumentException("댓글 쓰기 실패 : 작성자 id를 찾을 수 없습니다. ");
        });

        Board board = boardRepository.findById(replySaveRequestDto.getBoardId()).orElseThrow(() -> {
            return new IllegalArgumentException("댓글 쓰기 실패 : 게시글 id를 찾을 수 없습니다. ");
        });

        Reply reply = new Reply();
        reply.update(user, board, replySaveRequestDto.getContent());

        replyRepository.save(reply);
    }
}
