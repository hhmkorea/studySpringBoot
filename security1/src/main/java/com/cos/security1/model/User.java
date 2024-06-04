package com.cos.security1.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/**
 * packageName    : com.cos.security1.model
 * fileName       : User
 * author         : dotdot
 * date           : 2024-06-01
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-06-01        dotdot       최초 생성
 */

// ORM(Object Relational Mapping) -> Java(다른언어) Object -> 테이블로 매핑해주는 기술
@Builder // 빌더 패턴
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity // User 클래스가 MySQL에 테이블이 생성이 된다.
// @DynamicInsert // insert 시 null인 필드를 제외시켜줌.
public class User {
    @Id // PK
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 프로젝트에서 연결된 DB 넘버링 전략을 따라간다.
    private int id; // 오라클 : 시퀸스, mysql : auto_increment

    @Column(nullable = false, length = 100, unique = true)
    private String username; // 아이디

    @Column(nullable = false, length = 100) // 해쉬(비밀번호 암호화) 대비
    private String password;

    @Column(nullable = false, length = 50)
    private String email;

    // @ColumnDefault("user")
    // DB는 RoleType이라는게 없다.
    //@Enumerated(EnumType.STRING)
    //private RoleType role; // Enum을 쓰는게 좋다. // ADMIN, USER
    private String role; //USER, ADMIN
    //private String oauth; // kakao, google

    @CreationTimestamp // 시간 자동 입력
    private LocalDateTime createDate;
}
