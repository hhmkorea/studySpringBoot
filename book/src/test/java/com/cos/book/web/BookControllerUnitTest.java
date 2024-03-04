package com.cos.book.web;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

// 단위 테스트 : Controller만 테스트 (Controller 관련 로직만 띄우기)

@WebMvcTest // 메모리에 Controller, Filter, ControllerAdvice 가 뜸.
// @ExtendWith(SpringExtension.class)이 안에 돌어있음. Spring 환경에서 테스트할때 필수! Junit4에는 붙여야함.
public class BookControllerUnitTest {

}
