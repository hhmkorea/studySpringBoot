package com.cos.blog.model;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

/**
 * packageName    : com.cos.blog.model
 * fileName       : Board
 * author         : dotdot
 * date           : 2024-04-05
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-04-05        dotdot       최초 생성
 */
@Entity
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    private int id;

    @Column(nullable = false, length = 100)
    private String title;

    @Lob // 대용량 데이터
    private String conente; // 섬머노트 라이브러리 <html>태그가 섞여서 디자인이 됨.

    @ColumnDefault("0") // 기본값 0, 문자면 '0'
    private int count; // 조회수

    @ManyToOne // 연관관계, Many = Board, User = One
    @JoinColumn(name="userId")
    private User user; // DB는 오브젝트를 저장할 수 없다. FK, 자바는 오브젝트를 저장할 수 있다.

    @CreationTimestamp
    private Timestamp createDate;
}
