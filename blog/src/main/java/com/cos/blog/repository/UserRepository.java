package com.cos.blog.repository;

import com.cos.blog.model.User;
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

// DAO : Data Access Object
// 자동으로 bean 등록이 된다.
// @Repository // 생략 가능
public interface UserRepository extends JpaRepository<User, Integer> {

}
