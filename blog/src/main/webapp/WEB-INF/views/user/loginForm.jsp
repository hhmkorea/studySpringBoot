<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../layout/header.jsp" %>

<div class="container">
    <form action="/auth/loginProc" method="post">
        <div class="mb-3">
            <label for="username" class="form-label">Username</label>
            <input type="text" name="username" class="form-control" id="username" placeholder="Enter username">
        </div>
        <div class="mb-3">
            <label for="password" class="form-label">Password</label>
            <input type="password" name="password" class="form-control" id="password" placeholder="Enter password">
        </div>
        <button id="btn-login" class="btn btn-primary">로그인</button>
        <a href="https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=66c53837ec775df5a3d2f7c75cdc8e0b&redirect_uri=http://localhost:8000/auth/kakao/callback"><img height="38px" src="/image/kakao_login_button.png" /></a>
    </form>
</div>

<%@ include file="../layout/footer.jsp" %>
</html>
