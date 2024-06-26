package com.cos.blog.repository;

import com.cos.blog.model.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * packageName    : com.cos.blog.repository
 * fileName       : CommentRepository
 * author         : dotdot
 * date           : 2024-04-21
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-04-21        dotdot       최초 생성
 */
public interface ReplyRepository extends JpaRepository<Reply, Integer> {

    @Modifying
    @Query(value = "INSERT INTO reply(userId, boardId, content, createDate) VALUES (?1,?2,?3,now())", nativeQuery = true)
    int mSave(int userId, int boardId, String content); // 업데이트된 행의 갯수를 넘겨줌
}
