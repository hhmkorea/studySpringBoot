# JUnit 기초 
####
#### 테이블 생성할 쿼리 : 콘솔에 나오는 거 그대로 쓰면 됨
    drop table if exists Book cascade;
    create table Book (
    id bigint generated by default as identity,
    author varchar(20) not null,
    title varchar(50) not null,
    primary key (id)
    );