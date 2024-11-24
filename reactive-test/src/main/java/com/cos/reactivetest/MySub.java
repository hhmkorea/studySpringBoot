package com.cos.reactivetest;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public class MySub implements Subscriber<Integer>{

	private Subscription s;
	
	@Override
	public void onSubscribe(Subscription s) {
		System.out.println("구독자 : 구독 정보 잘 받았어.");
		this.s = s;
		System.out.println("구독자 : 나 이제 신문 1개씩 줘.");
		s.request(1); // 신문 3개씩 매일 매일 줘!!(백 프레셔) 소비자가 한번에 처리할 수 있는 개수를 요청, 12로 설정하면 한번에 10개 다 주고 끝남.
	}

	@Override
	public void onNext(Integer t) {
		System.out.println("onNext():" + t);		
		System.out.println("하루 지남");
		s.request(1);
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
