<!-- 로그인화면 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<title>로그인페이지</title>
	<%@include file="/WEB-INF/views/include/meta.jsp" %>
	<link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/signin.css'/>" />	
	<script type="text/javascript" src="<c:url value='/resources/js/signin.js'/>"></script>
	
	
	<style>
	main{    text-align: center;
    margin: 0 auto;
    width: 360px;   
     }
    #loginBtn{width: 100%;
    cursor: pointer;
    padding: 10px 0;}    
	h3{padding:20px 0;}
	
	.logo_login img{width:100%;}
	
	
	</style>
	
	
	
</head>
<body>
	<%@include file="/WEB-INF/views/include/header.jsp" %>
	<main>
		<!-- 이곳에 페이지의 컨텐츠가 담김 -->
		<div class="logo_login">
		<img src="/algo/resources/images/HatchfulExport-All (2)/logo_transparent.png" alt="" />
		</div>		
		<c:choose>
			<c:when test="${empty next}">
				<form method="POST" action="${pageContext.request.contextPath}/login/sign-in">					
			</c:when>
			<c:otherwise>
				<form method="POST" action="${pageContext.request.contextPath}/login/sign-in?next=${next}">
<%-- 			<form id="loginForm" action="<c:url value='/signin?next=${next}' />" method="POST">	 --%>
			</c:otherwise>
		</c:choose>		
<%-- 		<form method="POST" action="${pageContext.request.contextPath}/login/sign-in"> --%>
			<input type="text" name="email" id="email" placeholder="이메일">
			<span id="emailMsg"></span>
			<input type="password" name="pw" id="pw" placeholder="비밀번호">
			<span id="pwMsg" class="errmsg"><c:if test="${!empty svr_msg}" >${svr_msg }</c:if></span>
			<input type="submit" name="" id="loginBtn" value="로 그 인">
		</form>
	</main>	
	<%@include file="/WEB-INF/views/include/footer.jsp" %>
</body>
</html>
