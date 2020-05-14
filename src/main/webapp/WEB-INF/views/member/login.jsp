<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form"  prefix="form"%>
<c:set var="rootPath" value="${pageContext.request.contextPath }"/>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/include/include-head.jsp"%>
</head>
<body>
<%@ include file="/WEB-INF/views/include/include-header.jsp"%>
<section>
<form:form modelAttribute="memberVO" method="post">
<p>
<b>user name:</b>
<form:input path="u_name"/>
<form:errors path="u_name" />
</p>
<p>
<b>password:</b>
<form:input id="pass1" path="u_password" type="password"/>
<form:errors path="u_password" />
</p>
<button id="btn-login">submit</button>
<a id="regiLink" href="${rootPath }/member/register">Sign Up</a>
</form:form>
</section>
</body>
</html>