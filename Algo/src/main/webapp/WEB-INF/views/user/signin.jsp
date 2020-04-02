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
</head>
<body>
	<%@include file="/WEB-INF/views/include/header.jsp" %>
	<main>
		<!-- 이곳에 페이지의 컨텐츠가 담김 -->
		<h3>로그인</h3>
		<input type="text" name="email" id="email" placeholder="이메일">
		<span id="emailMsg"></span>
		<input type="password" name="pw" id="pw" placeholder="비밀번호">
		<span id="pwMsg" class="errmsg"><c:if test="${!empty svr_msg}" >${svr_msg }</c:if></span>
		<input type="submit" name="" id="loginBtn" value="로 그 인">
	</main>	
	<%@include file="/WEB-INF/views/include/footer.jsp" %>
</body>
</html>
