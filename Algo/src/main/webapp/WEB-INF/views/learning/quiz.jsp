<!-- 마무리 문제 풀기 화면 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<%@include file="/WEB-INF/views/include/meta.jsp"%>
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/css/quiz.css"/>" />
<script type="text/javascript"
	src="<c:url value="/resources/js/quiz.js"/>"></script>
</head>
<body>
	<%@include file="/WEB-INF/views/include/header.jsp"%>
	<main>
		<!-- 이곳에 페이지의 컨텐츠가 담김 -->
		<div>
		<table>
			<c:forEach var="quiz" items="${quizList }">
				<fmt:formatDate value="${quiz.createdAt }"
					pattern="yyyy/MM/dd HH:mm" var="cdate" />
				<tr>
					<td>${quiz.quizNum }</td>
					<td>${quiz.unitNum }</td>
					<td>${quiz.title }</td>
					
					<td>${cdate }</td>
				</tr>
			</c:forEach>
			</table>
		</div>
	</main>
	<%@include file="/WEB-INF/views/include/footer.jsp"%>
</body>
</html>
