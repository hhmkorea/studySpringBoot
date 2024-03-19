package shop.mtcoding.bank.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ResponseDto<T> { // 응답 DTO는 한번 리턴하면 수정할 일이 없어 final로 지정.
    private final Integer code; // 1 성공, -1 실패
    private final String msg;
    private final T data;
}
