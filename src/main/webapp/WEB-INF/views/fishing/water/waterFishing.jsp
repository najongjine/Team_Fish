<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="rootPath" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/include/include-head.jsp"%>
</head>
<body>
	<%@ include file="/WEB-INF/views/include/include-header.jsp"%>
	<section>
		<c:forEach items="${fistList }" var="vo" varStatus="i">
			<div class="container">
				<h2>${vo.title}</h2>
				<div class="card" style="width: 400px">
					<img class="card-img-top" src="${vo.firstimage }" alt="Card image"
						style="width: 100%">
					<div class="card-body">
						<h4 class="card-title">${vo.title }</h4>
						<p class="card-text">${vo.addr1 }</p>
						<p class="card-text">${vo.addr2 }</p>
						<a href="${rootPath }/fish/detail?contentid=${vo.contentid}" class="btn btn-primary">Detail</a>
					</div>
				</div>
				<br>
			</div>
		</c:forEach>
	</section>
</body>
</html>