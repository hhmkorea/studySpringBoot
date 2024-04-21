package com.cos.blog.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.List;

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
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    private int id;

    @Column(nullable = false, length = 100)
    private String title;

    //@Lob // 대용량 데이터 : TINYTEXT으로 등록되어 에러남.
    @Column(columnDefinition = "longblob")
    private String content; // 섬머노트 라이브러리 <html>태그가 섞여서 디자인이 됨.

    private int count; // 조회수

    // FetchType.EAGER : 해당 Entity(테이블) 조인해서 데이타 다 가져옴.
    // FetchType.LAZY : 해당 Entity(테이블) 조인해서 "필요하면" 데이타 가져올게!, 예:펼치기
    @ManyToOne(fetch = FetchType.EAGER) // 연관관계, Board = Many, User = One
    @JoinColumn(name="userId")
    private User user; // DB는 오브젝트를 저장할 수 없다. FK, 자바는 오브젝트를 저장할 수 있다.

    @OneToMany(mappedBy = "board", fetch = FetchType.EAGER) // Board = One, Reply = Many,
    // mappedBy : 연관관계의 주인이 아니다, FK(X), DB에 컬럼을 만들지 마세요.
    @JsonIgnoreProperties({"board"}) // Reply를 또 호출할때 무시하고 넘어감.
    @OrderBy("id desc")
    private List<Reply> replys; // Reply 안에 Board, User 리턴... 무한참조

    @CreationTimestamp
    private Timestamp createDate;
}
