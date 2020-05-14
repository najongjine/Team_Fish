<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="rootPath" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/include/include-head.jsp"%>
</head>
<script type="text/javascript">
$(function() {
	var mode="${MODE}"
	if(mode=="water"){
		$.ajax({
			url:"${rootPath}/fishUserWater/findAndShow",
			
			method:"GET",
			success:function(result){
				$(".userList").html("")
				$(".userList").html(result)
			}
		})
	}
	if(mode=="sea"){
		$.ajax({
			url:"${rootPath}/fishUserSea/findAndShow",
			
			method:"GET",
			success:function(result){
				$(".userList").html("")
				$(".userList").html(result)
			}
		})
	}
})
</script>
<body>
	<%@ include file="/WEB-INF/views/include/include-header.jsp"%>
	<c:if test="${MODE=='water' }">
	<section class="apiSearch">
		<form method="get" action="${rootPath }/fish/water">
			<label for="searchOption">검색</label> <select name="searchOption">
				<option value="titleSearch">제목으로 검색</option>
			</select> <input name="inputStr">
			<button id="userSearch">검색</button>
		</form>
		<br />
	</section>
	</c:if>
	<c:if test="${MODE=='sea' }">
	<section class="apiSearch">
		<form method="get" action="${rootPath }/fish/sea">
			<label for="searchOption">검색</label> <select name="searchOption">
				<option value="titleSearch">제목으로 검색</option>
			</select> <input name="inputStr">
			<button id="userSearch">검색</button>
		</form>
		<br />
	</section>
	</c:if>
	
	<section class="apiData row">
		<c:forEach items="${fishList }" var="vo" varStatus="i">
			<div class="container col-sm-3">
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
	<section class="apiPagi">
		<c:if test="${MODE=='water' }">
		<%@ include file="/WEB-INF/views/include/waterApiPagi.jsp" %>
		</c:if>
		<c:if test="${MODE=='sea' }">
		<%@ include file="/WEB-INF/views/include/seaApiPagi.jsp" %>
		</c:if>
	</section>
	<hr/>
	<br/>
	<section class="userList">
	</section>
	<%@ include file="/WEB-INF/views/chatbot.jspf" %>
</body>
</html>