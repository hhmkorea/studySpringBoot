CREATE USER 'cos'@'%' IDENTIFIED BY 'cos1234'; 
GRANT ALL PRIVILEGES ON *.* TO 'cos'@'%';
CREATE DATABASE photogram;

use rootdb;

-- 주요 테이블 
SELECT * FROM user; 
SELECT * FROM image;
SELECT * FROM subscribe;
SELECT * FROM likes;
SELECT * FROM comment;
-- DESC image;
-- TRUNCATE likes;

-- User (참고) Likes
-- Image (참고) Likes

-- 삭제 안되면, 자식먼저 삭제.
/*
DROP TABLE likes; 
DROP TABLE subscribe;
DROP TABLE image;
DROP TABLE user;
*/




-- 인기 페이지 
SELECT * FROM image i INNER JOIN likes l ON i.id = l.imageId;

SELECT i.* 
FROM image i INNER JOIN (SELECT imageId, 
									COUNT(imageId) likeCount 
									FROM likes 
									GROUP BY imageId
									)c 
ON i.id = c.imageId 
ORDER BY c.likeCount DESC;

-- 한줄로..
SELECT i.*  FROM image i INNER JOIN (SELECT imageId, COUNT(imageId) likeCount FROM likes  GROUP BY imageId )c ON i.id = c.imageId ORDER BY c.likeCount DESC;



-- 구독정보 리스트 
-- 2번 로그인 : userId = 1,3 
SELECT * FROM image 
WHERE userId IN (SELECT toUserId FROM subscribe WHERE fromUserId = 2)
ORDER BY id DESC
LIMIT 3; 

SELECT toUserId FROM subscribe WHERE fromUserId = 2;




-- 구독수 
SELECT COUNT(*) FROM subscribe WHERE fromUserId = 1;
-- 구독여부(ssal로 로그인, cos 페이지로 감) 
SELECT COUNT(*) FROM subscribe WHERE fromUserId = 1 AND toUserId = 2;
-- 로그인 (1 ssar) -- 구독정보(2 cos) 

-- 1번과 3번의 정보(toUserId)가 구독 모달에 출력 
SELECT * FROM subscribe WHERE fromUserId = 2; -- cos가 구독하고 있는 애들 
SELECT * FROM user WHERE id = 1 OR id = 3; --  cos가 구독하고 있는 애들 user 정보 

-- 조인 (user.id = subscribe.toUserid) 
SELECT u.id, u.username, u.profileImageUrl,  
FROM user u INNER JOIN subscribe s 
ON u.id = s.toUserId
WHERE s.fromUserId = 2;

-- 로그인(1), 화면(1,3) 
SELECT true FROM subscribe WHERE fromUserId = 1 AND toUserId = 1; -- 1번이 1번 구독하고 있다 -- 보일 필요 없음.
SELECT true FROM subscribe WHERE fromUserId = 1 AND toUserId = 3; -- 1번이 3번 구독하고 있다.

-- 가상 칼럼을 추가

SELECT u.id, u.username, u.profileImageUrl, 1 subscribeState 
FROM user u INNER JOIN subscribe s 
ON u.id = s.toUserId
WHERE s.fromUserId = 2
;

-- 스칼라 서브쿼리(단일행을 리턴) 
SELECT u.id, u.username, u.profileImageUrl, 
	(SELECT COUNT(*)) subscribeState 
FROM user u INNER JOIN subscribe s 
ON u.id = s.toUserId
WHERE s.fromUserId = 2
;

-- 구독 여부 완성 쿼리 
SELECT u.id, u.username, u.profileImageUrl, 
	(SELECT true FROM subscribe WHERE fromUserId = 1 AND toUserId = u.id) subscribeState 
FROM user u INNER JOIN subscribe s 
ON u.id = s.toUserId
WHERE s.fromUserId = 2
;

SELECT true FROM subscribe WHERE fromUserId = 1 AND toUserId = 3;

-- 동일 유저인지 판단 쿼리---로그인(1)
SELECT u.id, u.username, u.profileImageUrl, 
	if ((SELECT 1 FROM subscribe WHERE fromUserId = 1 AND toUserId = u.id), 1, 0) subscribeState ,
	if ((1 = u.id), 1, 0) equalUserState 
FROM user u INNER JOIN subscribe s 
ON u.id = s.toUserId
WHERE s.fromUserId = 2 -- fromUserId : 현재 페이지 주인id, cos 
;


