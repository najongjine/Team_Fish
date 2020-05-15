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
<script>
$(document).ready(function() {
	$(document).on("click","#btn-join",function(){
		let username=$("#username")
		let password=$("#password")
		let re_password=$("#re_password")
		
		if(username.val()==""){
			alert("아이디를 입력하세요")
			username.focus()
			return false
		}
		if(password.val()==""){
			alert("비밀번호를 입력하세요")
			password.focus()
			return false
		}
		if(re_password.val()==""){
			alert("비밀번호 재입력을 입력하세요")
			re_password.focus()
			return false
		}
		if(password.val()!=re_password.val()){
			alert("비밀번호와 비밀번호 확인이 다릅니다")
			password.focus()
			return false
		}
		
		$("form").submit()
	})
})
</script>
<body>
<%@ include file="/WEB-INF/views/include/include-header.jsp"%>
<%@ include file="/WEB-INF/views/include/include-mypage.jsp"%>

<section class="container mypage">
<h2>비밀번호 변경</h2>
	<div class="my-info">
		<form:form modelAttribute="memberVO" enctype="multipart/form-data" method="POST" class="list-group">
			
			<div class="list-group-item list-group-item-info">
			<label for="u_password">비밀번호 : </label>
			<form:input path="u_password" readonly="false" placeholder="비밀번호 입력" class="my-text"/>
			</div>
			
			<div class="list-group-item list-group-item-info">
			<label for="u_repassword">비밀번호 재입력 : </label>
			<form:input path="u_repassword" readonly="false" placeholder="비밀번호 재입력" class="my-text"/>
			</div>
			
			<button class="btn btn-success">비밀번호 변경</button>
			
		</form:form>
	</div>
</section>

</body>
</html>