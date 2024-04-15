<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../layout/header.jsp" %>

<div class="container">
    <form action="" method="post">
        <div class="mb-3">
            <label for="title" class="form-label">Title</label>
            <input type="text" class="form-control" id="title" placeholder="Enter title">
        </div>
        <div class="form-group">
            <label for="content">Content</label>
            <textarea class="form-control" rows="5" id="content" placeholder="Enter content"></textarea>
        </div>
        <button id="btn-save" class="btn btn-primary">글쓰기</button>
    </form>
</div>

<%@ include file="../layout/footer.jsp" %>
</html>
