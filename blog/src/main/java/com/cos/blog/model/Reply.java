package com.cos.blog.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/**
 * packageName    : com.cos.blog.model
 * fileName       : Reply
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
public class Reply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    private int id;

    @Column(nullable = false, length = 200)
    private String content;

    @ManyToOne // 연관관계, Replay = Many, Board = One
    @JoinColumn(name = "boardId")
    private Board board;

    @ManyToOne // 연관관계, Replay = Many, User = One
    @JoinColumn(name = "userId")
    private User user;

    @CreationTimestamp
    private LocalDateTime createDate;

    public void update(User user, Board board, String content) {
        setUser(user);
        setBoard(board);
        setContent(content);
    }

    @Override
    public String toString() {
        return "Reply [" +
                "id=" + id +
                ", content='" + content +
                ", board=" + board +
                ", user=" + user +
                ", createDate=" + createDate +
                ']';
    }
}
