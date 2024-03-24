package com.dotdot.jwtsecurity.repository;

import com.dotdot.jwtsecurity.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * packageName    : com.dotdot.jwtsecurity.domain
 * fileName       : MemberRepository
 * author         : dotdot
 * date           : 2024-03-24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-03-24        dotdot       최초 생성
 */
@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
    boolean existsByEmail(String email);
}
