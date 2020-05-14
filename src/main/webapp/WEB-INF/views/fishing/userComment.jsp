<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="rootPath" value="${pageContext.request.contextPath }" />

<script>
$(function() {

	$(document).on("click",".preply",function(){
		$(".replyreplyAtc").html("")
		let id=$(this).attr("id")
		let ufc_pid=$(this).data("ufc_pid")
		let ufc_fk=$(this).data("ufc_fk")
		$.ajax({
			url:"${rootPath}/fishUserWater/replyForm",
			data:{ufc_pid:ufc_pid, ufc_fk:ufc_fk},
			type:"GET",
			success:function(result){
				$("#"+id+"a").html("")
				$("#"+id+"a").html(result)
			}
		})
	})
	
})
</script>

<br/>
<hr/>
<section>
<c:choose>
	<c:when test="${commentList!=null}">
		<c:forEach items="${commentList}" var="vo">
		<section class="preply" id="${vo.ufc_id }" data-ufc_pid="${vo.ufc_id }" data-ufc_fk="${vo.ufc_fk }">
		id-${vo.ufc_id }, pid-${vo.ufc_pid } usr-${vo.ufc_username } : text-${vo.ufc_text }
		
		</section>
		<article id="${vo.ufc_id }a" class="replyreplyAtc">
		
		</article>
		</c:forEach>
	</c:when>
</c:choose>
</section>

<section>
<%@ include file="/WEB-INF/views/include/cmtPagi.jsp" %>
</section>