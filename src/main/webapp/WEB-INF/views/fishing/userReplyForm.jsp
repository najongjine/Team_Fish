<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="rootPath" value="${pageContext.request.contextPath }" />


<script>
$(document).ready(function() {
	
	//ajax를 사용해서 파일을 업로드 수행하고
	// 실제 저장된 파일 이름을 받아서 summernote에 기록된 내용중 img src="" 을 변경
	function upFile(file,editor) {
		var formData=new FormData()
		//upFile 변수에 file정보를 담아서 보내기 위한 준비
		/*
		editor.insertImage:= summernote 의 내장 함수를 callback 형태로
		호출해서 현재 summernote box에 표시하고자
		이미지의 src부분을 url값으로 대치
		img src="data:base64..." -> img src="UUIDfile.jpg" 형태로 변경
		*/
		formData.append('upFile',file) // 'upFile'은 컨트롤러에서 받을 파라메터 이름
		$.ajax({
			url:"${rootPath}/image_up",
			type:"POST",
			data:formData,
			contentType:false,
			processData:false,
			enctype:"multipart/form-data",
			success:function(result){
				result="${rootPath}/files/"+result
				$(editor).summernote('editor.insertImage',result)
			},
			error:function(){
				alert("서버통신오류")
			}
		})
	}//end ajax file upload
	//$('#summernoteNP').summernote()
	//$('#summernote').summernote();
	$('#summernoteReply').summernote({
		disableDragAndDrop : false,
		callbacks:{
			onImageUpload:function(files,editor,isEditted){
				for(let i=files.length-1;i>=0;i--){
					//파일을 한개씩 업로드할 함수
					upFile(files[i],this) // this==editor(summernote 자체)
				}
				
			}
		}
	});//end summer
});
	

</script>

		<form class="replyreply">
			ufc_pid:<input name="ufc_pid" value="${vo.ufc_pid }" type="readonly">
			ufc_fk:<input name="ufc_fk" value="${vo.ufc_fk }" type="readonly">
			<p>
				<textarea id="summernoteReply" class="summernote" name="ufc_text"></textarea>
			</p>
			<button id="btnReplyReply" type="button">reply</button>
		</form>
