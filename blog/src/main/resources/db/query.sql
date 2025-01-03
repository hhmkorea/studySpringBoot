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

select * from board;
desc board;

-- 비밀번호 초기화
# update user
# set password='$2a$10$0xL1d.Jzp7WFAbpDrAQe6.WyOh6JigqKontL6vtumKLYmf3hFizou' -- 1234
# where username in ('kaka');

-- update board set title = '안녕 + 이미지' where id = 1;

-- TINYTEXT으로 등록되어서 LONGBLOB으로 설정.
-- alter table board modify content longblob;s

-- 암호화 : AES_ENCRYPT /  AES_DECRYPT 방식 사용하기
# Update user
# Set password=HEX(AES_ENCRYPT('암호값', '1234')) where username = 'kaka';
#
# select username , CONVERT(AES_DECRYPT( UNHEX(password) , '1111') USING utf8) as passowrd
# from user WHERE username = 'kaka';

select *
from user
where
    username= 'yuyee@nate.com_3444864854'
;
desc user;
select * from board;
select * from reply;

-- delete from user where email = 'yuyee@nate.com';
insert into reply(content, boardId, userId, createDate) values ('첫 번째 댓글', 1, 2, now());
insert into reply(content, boardId, userId, createDate) values ('두 번째 댓글', 1, 2, now());
insert into reply(content, boardId, userId, createDate) values ('세 번째 댓글', 1, 2, now());
