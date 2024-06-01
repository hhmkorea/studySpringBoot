package com.cos.security1.repository;

import com.cos.security1.model.User;
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

// CRUD 함수를 JpaRepository가  들고 있음.
// @Repository라는 어노테이션이 없어도 IoC됨. 이유는 JpaRepository를 상속했기 때문.
public interface UserRepository extends JpaRepository<User,Integer> {
}
