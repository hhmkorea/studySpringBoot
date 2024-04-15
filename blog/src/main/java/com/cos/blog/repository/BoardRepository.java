package com.cos.blog.repository;

import com.cos.blog.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * packageName    : com.cos.blog.repository
 * fileName       : UserRepository
 * author         : dotdot
 * date           : 2024-04-07
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-04-07        dotdot       최초 생성
 */

public interface BoardRepository extends JpaRepository<Board, Integer> {

}
