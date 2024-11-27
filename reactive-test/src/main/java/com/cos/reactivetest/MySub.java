package com.cos.reactivetest;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public class MySub implements Subscriber<Integer>{

	private Subscription s;
	private int bufferSize = 3;
	
	@Override
	public void onSubscribe(Subscription s) {
		System.out.println("구독자 : 구독 정보 잘 받았어.");
		this.s = s;
		System.out.println("구독자 : 나 이제 신문 1개씩 줘.");
		s.request(bufferSize); // 신문 3개씩 매일 매일 줘!!(백 프레셔) 소비자가 한번에 처리할 수 있는 개수를 요청, -- 12로 설정하면 한번에 10개 다 주고 끝남.
	}

	// 2번 실행
	@Override
	public void onNext(Integer t) {
		System.out.println("onNext():" + t);		
		
		bufferSize--;
		
		if (bufferSize == 0) { // 소비 다하면 실행 
			System.out.println("하루 지남");
			bufferSize = 3;  // 3개 주고 하루 지남.
			s.request(bufferSize);
		}
	}

	@Override
	public void onError(Throwable t) {
		System.out.println("구독중 에러");		
	}

	@Override
	public void onComplete() {
		System.out.println("구독 완료");
	}

}
