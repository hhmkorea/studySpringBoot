package com.cos.chatapp;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@RequiredArgsConstructor // 생성자 만들어줌. 
@RestController // 데이터 리턴하는 서버
public class ChatController {
	
	private final ChatRepository chatRepository;

	@CrossOrigin // JS 요청을 허용 
	@GetMapping(value = "/sender/{sender}/receiver/{receiver}", produces = MediaType.TEXT_EVENT_STREAM_VALUE) // SSE 프로토콜, response 계속 유지.
	public Flux<Chat> getMsg(@PathVariable String sender, @PathVariable String receiver) { // 경로에서 넘어온 파라매터 받음.
		return chatRepository.mFindBySender(sender, receiver)
				.subscribeOn(Schedulers.boundedElastic());
	}

	@CrossOrigin // JS 요청을 허용 
	@PostMapping("/chat")
	public Mono<Chat> setMsg(@RequestBody Chat chat) { // 한 건만 데이터 리턴 
		chat.setCreatedAt(LocalDateTime.now(ZoneId.of("Asia/Seoul")));
		return chatRepository.save(chat); // Object를 리턴하면 자동으로 JSON 변환 (MessageConverer)
	}
}
