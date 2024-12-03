package com.cos.photogramstart.web.dto.subscribe;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SubscribeDto {
	private int id;
	private String username;
	private String profileImageUrl;
	private Integer subscribeState; // 로그인 사용자 기준 구독한 사람인지 아닌지
	private Integer equalUserState; // 로그인 사용자와 동일인 여부 
	// maria DB는 True 값을 받을때 Integer로 받아야함. 
}
