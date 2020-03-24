<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>메인화면</title>
	<meta charset="UTF-8">
	<!-- 절대경로 사용 -->
	<%@include file="/WEB-INF/views/include/meta.jsp" %>
	<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/main.css"/>" />
	<script type="text/javascript" src="<c:url value="/resources/js/main.js"/>"></script>
</head>
<body>
<%@include file="/WEB-INF/views/include/header.jsp" %>
	<main>
		<!-- 이곳에 페이지의 컨텐츠가 담김 -->
		<h1>
			Welcome to puer-tea's Algo Service!  
		</h1>
		
		<P>  The time on the server is ${serverTime}. </P>
	</main>	
<%@include file="/WEB-INF/views/include/footer.jsp" %>
</body>
</html>
