<!-- 도전과제 관리 화면 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html data-contextpath="${pageContext.request.contextPath}">
<head>
	<meta charset="UTF-8">
	<title>도전과제 관리</title>
	<%@include file="/WEB-INF/views/include/meta.jsp" %>	
	<style type="text/css">
		#challenge-list li.selected{
			background:lime;
		}
	</style>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/content-manager.js"></script>
	
</head>
<body>
	<%@include file="/WEB-INF/views/include/header.jsp" %>
	<main>
		<!-- 편집기 : 누르면 선택한 도전과제의 편집기 화면으로 감-->
		<button id="btn-editor" type="button">편집기</button>
		<!-- 도전과제를 추가함 -->
		<button id="btn-new-challenge" type="button">새 도전과제</button>
		<!-- 도전과제 리스트 -->
		<ul id="challenge-list">
			
		</ul>
		
	</main>	
	<%@include file="/WEB-INF/views/include/footer.jsp" %>
</body>
</html>
