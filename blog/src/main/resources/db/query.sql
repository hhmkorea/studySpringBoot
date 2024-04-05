-- 사용자 추가 : 유저이름@아이피주소
create user 'cos'@'%' identified by 'cos1234';
-- ON DB이름.테이블명
-- 권한 설정 : TO 유저이름@아이피주소
GRANT ALL PRIVILEGES ON *.* TO 'cos'@'%';
-- 데이터베이스 생성
CREATE DATABASE blog CHARACTER SET utf8 DEFAULT COLLATE utf8_general_ci;
-- 데이터베이스 사용
use blog;
-- 사용자 조회
select * from mysql.user;

-- 한글설정 확인
show variables like 'c%';

-- 테이블 확인
select * from user;
desc user;
