package com.cos.jwt.repository;

import com.cos.jwt.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * packageName    : com.cos.security1.repository
 * fileName       : UserRepository
 * author         : dotdot
 * date           : 2024-06-01
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-06-01        dotdot       최초 생성
 */

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
