package com.dotdot.security.controller.dto;

/**
 * packageName    : com.dotdot.security.controller.dto
 * fileName       : MemberResponseDto
 * author         : dotdot
 * date           : 2024-03-24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-03-24        dotdot       최초 생성
 */

import com.dotdot.security.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberResponseDto {
    private String email;

    public static MemberResponseDto of(Member member) {
        return new MemberResponseDto(member.getEmail());
    }
}
