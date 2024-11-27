package com.cos.fluxdemo.domain;

import org.springframework.data.annotation.Id;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor // final 부터 읽는 거에 대한 생성자 만들어줌.
@Data
public class Customer {

    @Id
    private Long id;
    private final String firstName;
    private final String lastName;

}