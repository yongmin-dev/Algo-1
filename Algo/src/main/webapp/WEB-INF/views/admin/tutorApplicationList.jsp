<!-- 튜터신청서 처리 화면 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
	<%@include file="/WEB-INF/views/include/meta.jsp" %>
	<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/tutor-application-list.css"/>" />	
	<script type="text/javascript" src="<c:url value="/resources/js/tutor-application-list.js"/>"></script>
</head>
<body>
	<%@include file="/WEB-INF/views/include/header.jsp" %>
	<main>
		<table >
			<tr>
				<th>제목</th>
				<th>내용</th>
				<th>신청자</th>
				<th>제출날짜</th>
				<th>승인여부</th>
			</tr>
			<c:forEach items="${datas.list }" var="application">
				<fmt:formatDate value="${application.createdAt}" pattern="yyyy/MM/dd" var="cdate" />
				<tr>
				<td>${application.title }</td>
				<td>${application.content }</td>
				<td>${application.username }</td>
				<td>${cdate}</td>
				<td><button>승인</button><button>거부</button></td>
				</tr>
			</c:forEach>
		</table>
	</main>	
	<%@include file="/WEB-INF/views/include/footer.jsp" %>
</body>
</html>
