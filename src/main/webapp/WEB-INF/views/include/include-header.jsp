<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="rootPath" value="${pageContext.request.contextPath }" />

<header class="jumbotron text-center">
	<a href="${rootPath }/"><h1>Korea Fishing</h1></a>
	<p>Welcome to Korea Fishing</p>
</header>
<nav>
<ul class="nav nav-pills nav-justified">
<li class="nav-item">
<a href="${rootPath }/fish/water?searchOption="" ">
		<p>Water Fishing</p>
	</a>
</li>

<li class="nav-item">
<a href="${rootPath }/fish/sea?searchOption="" ">
		<p>Sea Fishing</p>
	</a>
</li>

<li class="nav-item">
<a href="${rootPath }/mail ">
		<p>SendMail To Administrator</p>
	</a>
</li>

<c:if test="${U_NAME==null }">
<li class="nav-item">
<a href="${rootPath }/member/login">Login</a>
</li>

<li class="nav-item">
<a href="${rootPath }/member/register">Sign Up</a>
</li>
</c:if>

<c:if test="${U_NAME!=null }">
<li class="nav-item">
<a href="${rootPath}/mypage/view">
${U_NAME }(마이페이지)
</a>
</li>

<li class="nav-item">
<a href="${rootPath }/member/logout">
${U_NAME }(click to logout)</a>
</li>
</c:if>
</ul>
</nav>