<!-- 튜터 신청서 화면 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>튜터 등록</title>
	<%@include file="/WEB-INF/views/include/meta.jsp" %>
	<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/tutor-application.css"/>" />	
	<script type="text/javascript" src="<c:url value="/resources/js/tutor-application.js"/>"></script>
	
	
	<style>
	
	main{width:980px;margin:0 auto;}
	textarea{height:500px;}
	input[type="submit"]{padding:10px 15px;cursor:pointer;}
	input[type="button"]{padding:10px 15px;cursor:pointer;}
	.submit_wrap{float:right;}
	h2{padding:15px 0;}
	</style>
	
</head>
<body>
	<%@include file="/WEB-INF/views/include/header.jsp" %>
	<main>
		<!-- 이곳에 페이지의 컨텐츠가 담김 -->
		<form:form action="${pageContext.request.contextPath}/user/apply" method="post" 
		enctype="multipart/form-data"
		modelAttribute="tutorApplicationVO">
		<h2>튜터 신청</h2>		
		<form:label path="title">제목</form:label>
		  <form:input path="title" type="text"/>
		  <form:errors path="title" />			 
		  <form:label path="content">본문</form:label>
		  <textarea name="content" wrap="off" ></textarea><errors path="content" />
			
		  <form:label path="file">첨부</form:label>
		  <form:input type="file" path="file" />
		  <form:errors path="file" />
		</form:form>
		<div class="submit_wrap">
			<input type="submit" value="신청" />
			<input type="button" value="취소" />
		</div>
	</main>	
	<%@include file="/WEB-INF/views/include/footer.jsp" %>
</body>
</html>
