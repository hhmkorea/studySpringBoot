package com.cos.blog.model;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

/**
 * packageName    : com.cos.blog.model
 * fileName       : User
 * author         : dotdot
 * date           : 2024-04-05
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-04-05        dotdot       최초 생성
 */

// ORM(Object Relational Mapping) -> Java(다른언어) Object -> 테이블로 매핑해주는 기술
@Entity // User 클래스가 MySQL에 테이블이 생성이 된다.
public class User {

    @Id // PK
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 프로젝트에서 연결된 DB 넘버링 전략을 따라간다.
    private int id; // 오라클 : 시퀸스, mysql : auto_increment
    @Column(nullable = false, length = 30)
    private String username; // 아이디
    @Column(nullable = false, length = 100) // 해쉬(비밀번호 암호화) 대비
    private String password;
    @Column(nullable = false, length = 50)
    private String email;
    @ColumnDefault("'user'") // 문자라는걸 알려줌.
    private String role; // Enum을 쓰는게 좋다. 도메인(범위) 설정. // admin, user, manager... String 타입은 오타가 우려됨.
    @CreationTimestamp // 시간 자동 입력
    private Timestamp createDate;

}