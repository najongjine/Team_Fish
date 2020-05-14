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
		<form:form method="post" enctype="multipart/form-data" modelAttribute="userVO">
			<p>
				<b>uf_username: ${userVO.uf_username }</b> <form:input path="uf_username" type="hidden"/>
			</p>
			<p>
				<b>date: ${userVO.uf_date }</b> <form:input path="uf_date" type="hidden"/>
			</p>
			<p>
				<b>title:</b> <form:input path="uf_title" />
			</p>
			<p>
				<b>addr1:</b> <form:input path="uf_addr1" />
			</p>
			<p>
				<b>addr2:</b> <form:input path="uf_addr2" />
			</p>
			<p>
				<b>content:</b> <form:textarea class="summernote" path="uf_text" ></form:textarea>
			</p>
			<p>
				<b>pictures:</b> <input type="file" multiple="multiple" name="uploaded_files">
			</p>
			<button>submit</button>
		</form:form>
		<section>
		<br/>
		<hr/>
			<c:choose>
				<c:when test="${picsList!=null }">
					<c:forEach items="${picsList}" var="vo">
						<p>
						<img src="${rootPath }/files/${vo.ufp_uploadedFName }">
						<c:if test="${MODE=='water' }">
						<a href="${rootPath }/fishUserWater/deletePic?strUfp_id=${vo.ufp_id}&strFk=${vo.ufp_fk}">
						<button>deletePic</button>
						</a>
						</c:if>
						<c:if test="${MODE=='sea' }">
						<a href="${rootPath }/fishUserSea/deletePic?strUfp_id=${vo.ufp_id}&strFk=${vo.ufp_fk}">
						<button>deletePic</button>
						</a>
						</c:if>
						</p>
					</c:forEach>
				</c:when>
				<c:otherwise>
				</c:otherwise>
			</c:choose>
		</section>
	</section>
	<%@ include file="/WEB-INF/views/chatbot.jspf" %>
</body>
</html>