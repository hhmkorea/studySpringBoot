package com.cos.blog.contorller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * packageName    : com.cos.blog.contorller.dto
 * fileName       : ResponseDto
 * author         : dotdot
 * date           : 2024-04-10
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-04-10        dotdot       최초 생성
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseDto<T> { // <T> : Generic
    HttpStatus status;
    T data;
}
