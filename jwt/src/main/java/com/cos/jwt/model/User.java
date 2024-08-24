package com.cos.jwt.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * packageName    : com.cos.jwt.model
 * fileName       : User
 * author         : dotdot
 * date           : 2024-08-21
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-08-21        dotdot       최초 생성
 */

@Data // Getter, Setter
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // MYSQL이면 auto increment
    private long id;
    private String username;
    private String password;
    private String roles; // USER, ADMIN ---> security 최신 버전에서는 권한 적용시 ROLE_ 쓰지 않음.

    public List<String> getRoleList() { // size 2개, 0:USER, 1:ADMIN
        if(this.roles.length() > 0) {
            return Arrays.asList(this.roles.split(","));
        }
        return new ArrayList<>();
    }


}
