<!-- 학습 컨텐츠 관리 화면 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html data-contextpath="${pageContext.request.contextPath}">
<head>
	<meta charset="UTF-8">
	<title>학습 컨텐츠 관리</title>
	<%@include file="/WEB-INF/views/include/meta.jsp" %>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/content-manager.js"></script>
	<script type="text/javascript" src="<c:url value="/resources/js/learning-content-manager.js"/>"></script>
</head>
<body>
	<%@include file="/WEB-INF/views/include/header.jsp" %>
	<main>
		<!-- 과목리스트 -->
		<ul id="subject-list">
		</ul>
		
		<!-- 단원 depth 왼쪽 -->
		<button id="btn-move-left">왼쪽</button>
		
		<!-- 단원 depth 오른쪽 -->
		<button id="btn-move-right">오른쪽</button>
		
		<!-- 단원 편집기 -->
		<button id="btn-unit-editor">편집기</button>
		
		<!-- 단원리스트 -->
		<ul id="unit-list">
		</ul>
		<!-- 이곳에 페이지의 컨텐츠가 담김 -->
	</main>	
	<%@include file="/WEB-INF/views/include/footer.jsp" %>
</body>
</html>
