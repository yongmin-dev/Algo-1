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



			<c:forEach var="quiz" items="${quizList }">
			${quiz.content }
		<ul>


					<c:forEach var="answer" items="${quiz.answerList }">

						<li>${answer.content }
					</c:forEach>
				</ul>
			</c:forEach>


		</div>








	</main>
	<%@include file="/WEB-INF/views/include/footer.jsp"%>
</body>
</html>
