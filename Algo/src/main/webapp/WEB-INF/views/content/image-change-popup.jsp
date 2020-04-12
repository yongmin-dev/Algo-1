<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html data-contextpath="${pageContext.request.contextPath}">
<head>
<meta charset="UTF-8">
<title>이미지를 변경하세요</title>
	<%@include file="/WEB-INF/views/include/meta.jsp" %>	

	<style>
		html,body{
			margin:0px;
			height:100%;
		}
		img{
			height:400px;
			width:500px;
		}
	</style>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/image-changer.js"></script>
</head>
<body>
	<c:choose>
		<c:when test="${ hasImage}">
			<img id="subject-image-preview" src="${pageContext.request.contextPath}/content/learning/subject/image/${subjectNum}">
		</c:when>
		<c:otherwise>
			<img id="subject-image-preview" src="${pageContext.request.contextPath}/resources/images/no-subject-image.jpg">
		
		</c:otherwise>
	</c:choose>
	<input type="file" id="subject-image-input"/>
	<button id="btn-image-submit" data-num="${subjectNum}">이미지 전송하기</button>
</body>
</html>