package com.dotdot.jwtsecurity.repository;

import com.dotdot.jwtsecurity.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * packageName    : com.dotdot.jwtsecurity.repository
 * fileName       : RefreshTokenRepository
 * author         : dotdot
 * date           : 2024-03-25
 * description    : Member ID값으로 토큰 가져오는 저장소
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-03-25        dotdot       최초 생성
 */
@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByKey(String key);
}
