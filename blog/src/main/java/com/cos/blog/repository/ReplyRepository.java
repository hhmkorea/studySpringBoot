package com.cos.blog.repository;

import com.cos.blog.model.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

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
}
