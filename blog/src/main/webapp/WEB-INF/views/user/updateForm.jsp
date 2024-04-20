<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../layout/header.jsp" %>

<div class="container">
    <%--<form action="/user/join" method="POST">--%>
    <form>
        <input type="hidden" id="id" value="${principal.user.id}">
        <div class="mb-3">
            <label for="username" class="form-label">Username</label>
            <input type="text" value="${principal.user.username}" class="form-control" id="username" placeholder="Enter username" readonly>
        </div>
        <c:if test="${empty principal.user.oauth}"> <!-- oauth 값이 비워있으면 수정 가능 -->
            <div class="mb-3">
                <label for="password" class="form-label">Password</label>
                <input type="password" class="form-control" placeholder="Enter password" id="password">
            </div>
        </c:if>
        <div class="mb-3">
            <label for="email" class="form-label">Email</label>
            <input type="email" value="${principal.user.email}" class="form-control" id="email" placeholder="Enter email" readonly>
        </div>
    </form>

    <button id="btn-update" class="btn btn-primary">회원수정</button>
</div>

<script src="/js/user.js"></script>
<%@ include file="../layout/footer.jsp" %>
</html>
