<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    <c:set var="rootPath" value="${pageContext.request.contextPath}"/>







<style>


.marineListBoxDiv{
	border: 1px solid red;
    width: 90%;
    margin: 0 auto;
    flex-wrap: wrap;
    display: flex;
    padding: 0;
   
}

.listContent{
	border: 1px solid black;
	width:150px;
	height:150px;
	margin: 0 auto;
	display: none;
}

.listContentTitle{
	border: 1px solid black;
	width:150px;
	height:150px;
	margin: 0 auto;
	font-weight: bold;
	display: none;
}

.listThumbnail{
	width:150px;
	height:150px;
}

.img-thumbnail{
	border:none;
}

.allCoverBoxDiv{
	border:1px solid green;
	display: flex;
}

.prevBtn{
	margin: auto 0;
}

.nextBtn{
	margin: auto 0;
}

</style>


    
<script>
$(function(){
	
	
	var marineList
	
	

	// ajax로 List 불러오기
	
	
	//Math.floor(): 소수점 버림
	//parseFloat
	// 파싱안됨 NaN뜸
	var mapX = $('#mapX').text()
	var mapY = $('#mapY').text()
	

	var xIndex = mapX.indexOf('.')
	var resultX = mapX.substring(0,xIndex).replace(" ", "")

	
	var YIndex = mapY.indexOf('.')
	var resultY = mapY.substring(0,YIndex).replace(" ", "")
	
	
	var dataPerPage = 10 // 한 페이지에 나타낼 데이터 수
	var pageCount = 5 // 한 화면에 나타낼 페이지 수
	

	

		
	
	
		
		var lengthVal
		
		
			$.ajax({
				
				url:"${rootPath}/marinelifeapi/getXYmarine", data:{mapX:resultX, mapY:resultY}, type:'get',
				success:function(result){
					// 리스트 넘어옴
					
					
					marineList = result
					
				
					
					
					$.each(marineList,function(i){
					
						
						//alert(i)
						
						
						// 제목만 제공하는 생물
						if(marineList[i].fullInfo == false){
							
							$('.marineListBoxDiv').append("<div id='id" + i + "'  class='listContentTitle'>" + marineList[i].title + "<div class='descriptionAtTitle' value='" + marineList[i].description + "'></div></div>")
							
						// 모든 정보 제공하는 생물 중에서		
						}else{
						
							// 섬네일이 있는 생물(정보 제공 완전체)
							if(marineList[i].thumbnail.length > 0 ){
							$('.marineListBoxDiv').append("<div id='id" + i + "' class='listContent'><img class='img-thumbnail listThumbnail' src='"  +  marineList[i].thumbnail  +"'/><div class='title' value='"+ marineList[i].title + "'></div><div class='description' value='"+ marineList[i].description + "'></div></div>")
							}
							// 섬네일이 없는 생물
							else{
								$('.marineListBoxDiv').append("<div id='id" + i + "' class='listContentTitle'>" + marineList[i].title + "<div class='descriptionAtTitle' value='" + marineList[i].description + "'></div></div>")
							}
						
						}
						
						
						
						if(i < 10){
							var id = 'id'+i
							$('#'+id).css('display','block')
						}
						
						
					})
					
					
					
					
					
				},error:function(){
					alert('서버 에러')
				}
				
			})

		


	
	
	
})
</script>




<div class="allCoverBoxDiv">

<div class="prevBtn">왼쪽</div>

<div class="marineListBoxDiv">

<input id="marineListLength" type="hidden">

<input id="currentPage" type="hidden">

</div>

<div class="nextBtn">오른쪽</div>

</div>