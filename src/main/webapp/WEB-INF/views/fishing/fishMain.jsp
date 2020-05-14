<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="rootPath" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/include/include-head.jsp"%>
<title>Detail</title>
</head>
<body>
	<%@ include file="/WEB-INF/views/include/include-header.jsp"%>
	<section>
	<a href="${rootPath }/fish/water?searchOption="" ">
		<p>Water Fishing</p>
	</a>
	<a href="${rootPath }/fish/sea?searchOption="" ">
		<p>Sea Fishing</p>
	</a>
	</section>
	<%@ include file="/WEB-INF/views/chatbot.jspf" %>
</body>
</html>