<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<c:set var="rootPath" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/include/include-head.jsp"%>
<title>insert</title>
<script type="text/javascript">
/*$(function() {
	let text="${userVO.uf_text }"+""
	alert(text)
	$("#uf_text").val(text)
})*/
</script>
</head>
<body>
	<%@ include file="/WEB-INF/views/include/include-header.jsp"%>
	<section>
	<h3>운영자에게 메일 보내기</h3>
		<form method="post">
		<div>
			<label>나의 이메일 주소: </label>
			<input name="from_email">
		</div>
		<div>
			<label>제목: </label>
			<input name="subject">
		</div>
		<div>
			<textarea name="content" cols="100" rows="10"></textarea>
		</div>
		<div>
			<button>메일보내기</button>
		</div>
		</form>
	</section>
	<%@ include file="/WEB-INF/views/chatbot.jspf" %>
</body>
</html>