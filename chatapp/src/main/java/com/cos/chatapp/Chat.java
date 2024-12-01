package com.cos.chatapp;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "chat")
public class Chat {
	@Id
	private String id;	// MongoDB에 Bson 타입이 있는데 String 타입을 쓰는게 더 편함.
	private String msg;
	private String sender; // 보내는 사람 
	private String receiver; // 받는 사람(귓속말)
	private String roomNum; // 방번호 
	
	private LocalDateTime createdAt;

	
}
