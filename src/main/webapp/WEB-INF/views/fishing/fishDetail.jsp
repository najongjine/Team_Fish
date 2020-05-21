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
<script src="https://apis.openapi.sk.com/tmap/jsv2?version=1&appKey=l7xxe287b2ab163049a58075ca366a804bea"></script>
<script type="text/javascript" >
$(function(){
	var map;
    let mapx=${fishVO.mapx}
    let mapy=${fishVO.mapy}
    map=new Tmapv2.Map("map_div",{
      width:'90%',
      height:'500px',
      center:new Tmapv2.LatLng(mapy,mapx),
      zoom: 15
    })
    //map.setLanguage("EN",true); // 영문
    var marker = new Tmapv2.Marker({
		position: new Tmapv2.LatLng(mapy,mapx), //Marker의 중심좌표 설정.
		map: map //Marker가 표시될 Map 설정..
	})
	$("#daumMap").hide()
	$("#showDaumMap").click(function() {
		$("#daumMap").toggle()
	})
	
	
	$.ajax({
		url:"${rootPath}/marinelifeapi/marinedetailpage", type:'get',
		success:function(result){
			
			$('.marineBoxDiv').html(result)
			
		},error:function(){
			alert('서버 에러')
		}
	})
	
	
  })
</script>
<body>
	<%@ include file="/WEB-INF/views/include/include-header.jsp"%>
	
	<section>
	<c:if test="${fishVO.booktour!=null}">
		<p>Introduced in Text book :${fishVO.booktour}</p>
	</c:if>
	<c:if test="${fishVO.homepage!=null}">
		<p>Home Page: ${fishVO.homepage}</p>
	</c:if>
	<c:if test="${fishVO.tel!=null}">
		<p>tel: ${fishVO.tel}</p>
	</c:if>
	<c:if test="${fishVO.telname!=null}">
		<p>tel name: ${fishVO.telname}</p>
	</c:if>
	
	<p>title: ${fishVO.title}</p>
	<p><img src="${fishVO.firstimage}" ></p>
	<p><img src="${fishVO.firstimage2}"></p>
	<p>area code: ${fishVO.areacode}</p>
	<p>city code: ${fishVO.sigungucode}</p>
	<p>Addr 1: ${fishVO.addr1}</p>
	<p>Addr 2: ${fishVO.addr2}</p>
	<p>zipcode: ${fishVO.zipcode}</p>
	<p id="mapY">${fishVO.mapx}</p>
	<p id="mapX">${fishVO.mapy}</p>
	<p>mlevel: ${fishVO.mlevel}</p>
	<p>description : ${fishVO.overview}</p>
	</section>
	
	
	<div class="marineBoxDiv">
	
	
	
	</div>
	
	<section id="daumMapButton">
	<br/>
	<hr/>
	<button id="showDaumMap">use daum map(korean version)</button>    
    </section>
    
    <section id="daumMap">
    <br/>
    <iframe src="https://m.map.kakao.com/" width="100%" height="1000px"></iframe>
    </section>
	
	<section id="map_div">
	<hr/>
	<p>map: (it only shows in korean... sorry for inconvenience... for detailed explanation, please use google map </p>
	<p>or navigation application on your gadget.)</p>
    </section>
    
    <%@ include file="/WEB-INF/views/chatbot.jspf" %>
</body>
</html>