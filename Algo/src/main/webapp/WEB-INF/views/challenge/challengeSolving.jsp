<!-- 도전과제 풀기 화면  -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html data-contextpath="${pageContext.request.contextPath}">
<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
	<%@include file="/WEB-INF/views/include/meta.jsp" %>		
	<script type="text/javascript" src="<c:url value="/resources/js/challenge-solving.js"/>" ></script>
	
</head>
<body>
	<%@include file="/WEB-INF/views/include/header.jsp" %>
	<main>
	<!-- 제목 -->
	제목:${challenge.title}<br>
	
	<!-- 작성자 -->
	작성자:${challenge.username}<br>
	
	<!-- 내용 -->
	내용: ${challenge.content}<br>
	
	<!-- 제한시간 -->
	시간제한: ${challenge.limitTime}<br>
	
	<!-- 제한메모리 -->
	메모리제한: ${challenge.limitMemory}<br>
	
	<!-- 코드에디터 -->
	<textarea id="code-editor" rows="20" cols="40"></textarea>
	
	<!-- 전송버튼 -->
	<button id="btn-submit-code" data-context-path="${pageContext.request.contextPath}" data-cnum="${challenge.CNum}">제출</button>
	
	<!-- 결과표시	-->
	<span id="result-msg"></span>
	<div id="after-submit"></div>
	</main>	
	<%@include file="/WEB-INF/views/include/footer.jsp" %>
</body>
</html>