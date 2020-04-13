<!-- 단원 학습내용 화면 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title> ${unit.title} </title>
<%@include file="/WEB-INF/views/include/meta.jsp"%>
<%-- <link rel="stylesheet" type="text/css"	href="<c:url value="/resources/css/unit-content.css"/>" /> --%>
<script type="text/javascript"	src="<c:url value="/resources/js/learning/unit-content.js"/>"></script>
	
	<style>
	main{width:980px;margin:0 auto;overflow:hidden;}
	
	nav{width:25%;float:left;}
	nav h2{padding:5px 0;}
	nav h1{padding:5px;font-weight:500;padding:20px 0 0 0;}
	nav h1:hover{font-weight:bold;}
	.unitContent_wrap{width:75%;float:left;}
	.unitContent_wrap h1{text-align:center;padding:20px 0;}
	.content_text{
/* 	height:500px; */
	border:2px solid #eaeaea;}
	.final{background: #555273;}
	.final a{color:#fff;font-size:18px;}
	
	</style>
	
	
</head>
<body>
	<%@include file="/WEB-INF/views/include/header.jsp"%>
	<main>
		
		<!-- 다른 단원의 목록		 -->
		<nav>
			<c:forEach var="unitItem" items="${unitList }">
<%-- 				${unitItem}${unit} --%>
				<h2>
					<a href="${pageContext.request.contextPath }/learning/unit/${unitItem.unitNum }">
						<c:if test="${unit.unitNum eq unitItem.unitNum }">
							<strong>
						</c:if>
						<span>${unitItem.chapterDepth}</span>${unitItem.title }
						<c:if test="${unit.unitNum eq unitItem.unitNum }">
							</strong>
						</c:if>
					</a>
				</h2>
			</c:forEach>
		</nav>
		
		
		
		
		
		
		
		<div class="unitContent_wrap">
			<div id="unitContent">
			<!-- 단원 제목-->
				<h1 id="unit-title"><span class="depth">${unit.chapterDepth} </span>${unit.title}</h1>
			</div>
			<!-- 단원 내용-->
			<div class="content_text">
				${unit.content}
			</div>
			<!-- 마무리 문제 풀기-->
			<div class="final">
				<h1><a href="${pageContext.request.contextPath }/learning/quiz/${unit.unitNum }">
					마무리 문제 풀기</a></h1>
			</div>
		</div>



	</main>
	<%@include file="/WEB-INF/views/include/footer.jsp"%>
</body>
</html>


