<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!doctype html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Bootstrap demo</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body>
<%--<nav class="navbar bg-dark border-bottom border-body" data-bs-theme="dark">--%>
<nav class="navbar bg-dark navbar-expand-lg bg-body-tertiary" data-bs-theme="dark">
    <div class="container-fluid">
        <a class="navbar-brand" href="/blog/">Dotdot</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNavAltMarkup"
                aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
            <div class="navbar-nav">
                <a class="nav-link active" aria-current="page" href="/user/login">로그인</a>
                <a class="nav-link" href="/user/join">회원가입</a>
            </div>
        </div>
    </div>
</nav>
<div class="container">

    <div class="card m-2">
        <div class="card-body">
            <h5 class="card-title">제목 적는 부분</h5>
            <p class="card-text">내용 적는 부분</p>
            <a href="#" class="btn btn-primary">상세보기</a>
        </div>
    </div>
    <div class="card m-2">
        <div class="card-body">
            <h5 class="card-title">제목 적는 부분</h5>
            <p class="card-text">내용 적는 부분</p>
            <a href="#" class="btn btn-primary">상세보기</a>
        </div>
    </div>
    <div class="card m-2">
        <div class="card-body">
            <h5 class="card-title">제목 적는 부분</h5>
            <p class="card-text">내용 적는 부분</p>
            <a href="#" class="btn btn-primary">상세보기</a>
        </div>
    </div>

</div>
<footer class="py-3 my-4 bg-body-secondary">
    <p class="text-center text-body-secondary">💻Create by Dotdot<p>
    <p class="text-center text-body-secondary">📞010-2222-7777<p>
    <p class="text-center text-body-secondary">📧test@test.com<p>
</footer>
</body>
</html>
