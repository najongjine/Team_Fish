<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="rootPath" value="${pageContext.request.contextPath}"/>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/include/include-head.jsp"%>
<style>

h2{
text-align: center;
}

.mypage{
width: 500px;
border: 2px solid blue;
}

.my-info{
margin: 20px;
}

.my-text{
margin-left: auto;
margin-right: 0;
}

button{
margin-top: 10px;
}

</style>
</head>
<body>
<%@ include file="/WEB-INF/views/include/include-header.jsp"%>
<%@ include file="/WEB-INF/views/include/include-mypage.jsp"%>

<section class="container mypage">
<h2>회원정보 수정</h2>
	<div class="my-info">
		<form:form modelAttribute="memberVO" enctype="multipart/form-data" method="POST" class="list-group">
		
			<div class="list-group-item list-group-item-primary">
			<label for="u_name">ID : </label>
			<form:input path="u_name" readonly="true" class="my-text"/>
			</div>
			
			<!-- 
			<div class="list-group-item list-group-item-primary">
			<label for="u_password">비밀번호 : </label>
			<form:input path="u_password" readonly="true" class="my-text"/>
			</div>
			 -->
			
			<div class="list-group-item list-group-item-info">
			<label for="email">E-mail : </label>
			<form:input path="email" readonly="false" class="my-text"/>
			</div>
			
			<div class="list-group-item list-group-item-info">
			<label for="phone">Phone : </label>
			<form:input path="phone" readonly="false" class="my-text"/>
			</div>
			
			<div class="list-group-item list-group-item-info">
			<label for="address">Address : </label>
			<form:input path="address" readonly="false" class="my-text"/>
			</div>
			
			<input name="uploaded_files" type="file">
			
			<button class="btn btn-success">수정</button>
			
		</form:form>
	</div>
</section>

</body>
</html>