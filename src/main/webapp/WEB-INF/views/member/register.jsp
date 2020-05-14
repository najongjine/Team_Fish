<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="rootPath" value="${pageContext.request.contextPath }"/>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/include/include-head.jsp" %>
</head>
<style>
input{
margin-top:2vh;
margin-bottom: 2vh;
}
button{
margin-left: 1vw;
margin-right:1vw;
margin-top:2vh;
margin-bottom: 2vh;
}
.message{
color: green;
font-weight: bold;
font-size: 0.3rem;
}
</style>
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
	$(document).on("blur","#username",function(){//입력박스에서 포커스가 벗어났을때
		let username=$(this).val()
		if(username==""){
			$("#m_username").text("아이디는 반드시 입력해야 합니다")
			$("#m_username").focus()
			return false
		}
		$.ajax({
			url:"${rootPath}/user/idcheck",
			method:"GET",
			data:{username:username},
			success:function(result){
				if(result=="EXISTS"){
					$("#m_username").text("이미 가입된 사용자 이름입니다")
					$("#m_username").css("color","red")
					$("#m_username").focus()
					return false
				} else{
					$("#m_username").text("사용가능 합니다")
					return false
				}
			},
			error:function(){
				$("#m_username").text("서버통신오류")
				return false
			}
		})
	})
})
</script>
<body>
<%@ include file="/WEB-INF/views/include/include-header.jsp" %>

<form:form modelAttribute="memberVO" action="${rootPath }/member/register_next" method="post" class="form-group container">
<h2 class="jumbotron">회원가입</h2>
<form:input path="u_name" placeholder="User ID" class="form-control"/>
<p class="message" id="m_username"></p>

<form:input type="password" path="u_password" placeholder="password" class="form-control"/>
<form:input type="password" path="u_repassword" placeholder="re_password" class="form-control"/>
<button class="btn btn-outline-primary" id="btn-join" type="button">회원가입</button>

<a href="${rootPath }/password/resetPass">
<button class="btn btn-outline-success" id="btn-loss" type="button">비밀번호를 잃어버렸다...</button>
</a>
</form:form>
</body>
</html>