# JWT Security Test Pjt
###
#### * 이슈 발생 : SecurityConfig에서 apply()가 안되는 이슈 발생. 
#### * 이슈 원인 : Spring Boot 3.0, Spring Security 6.0 버전 이슈로 인한 이전 버전 지원 불가
#### * 이슈 처리 : https://bcp0109.tistory.com/301 사이트 참고해서 테스트
+ 실습 주제 : Spring Security 와 JWT 겉핥기
+ 실습 내용 : Spring Boot + JWT + Security
###
#### 1. Member 도메인
+ Member
+ MemberRepository
+ MemberService
+ MemberController
+ application.yml: h2 database 설정과 jwt secret key 설정
####
#### 2. JWT 관련
+ TokenProvider: 유저 정보로 JWT 토큰을 만들거나 토큰을 바탕으로 유저 정보를 가져옴
+ JwtFilter: Spring Request 앞단에 붙일 Custom Filter
####
#### 3. Spring Security 관련 
+ JwtSecurityConfig: JWT Filter 를 추가
+ JwtAccessDeniedHandler: 접근 권한 없을 때 403 에러
+ JwtAuthenticationEntryPoint: 인증 정보 없을 때 401 에러
+ SecurityConfig: 스프링 시큐리티에 필요한 설정

        *** Spring Security 6.0 변경사항 ***
        람다식으로 변경.
        apply() 지원 안함 ---> addFilterBefore()로 구현해야함.

+ SecurityUtil: SecurityContext 에서 전역으로 유저 정보를 제공하는 유틸 클래스
