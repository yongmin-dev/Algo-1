<!-- 마무리 문제 풀기 화면 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html data-contextpath="${pageContext.request.contextPath}">
<head>
<meta charset="UTF-8">
<title>마무리 문제 풀기</title>
<%@include file="/WEB-INF/views/include/meta.jsp"%>
<style type="text/css">
	#quiz-list > li.passed .quiz-title{
		background: #11ff33;
	}
	#quiz-list > li.failed .quiz-title{
		background: #ffbbbb;
	}
	ul.answer-list > li.wrong-answer{
		background #ff1111;
	}
	ul.answer-list > li.correct-answer{
		background:#11ffbb;
	}
</style>
<script type="text/javascript" src="<c:url value="/resources/js/learning/quiz.js"/>"></script>
</head>
<body>
	<%@include file="/WEB-INF/views/include/header.jsp"%>
	<main>
		<div>
			<h2 id="unit-title"><span class="unit-depth">${unit.chapterDepth}</span>${unit.title} 의 마무리문제</h2>
		</div>
		<!-- 퀴즈가 표시됨 -->
		<div>
			<ul id="quiz-list">
				<c:forEach var="quiz" items="${quizList }" varStatus="status">
				<!-- 문제 제목-->
				<li class="quiz-item" data-num="${quiz.quizNum}">
				<form id="quiz-${quiz.quizNum}">
				<h4 class="quiz-title">${quiz.title }</h4>		
				<div class="quiz-content">${quiz.content }</div>
				<ul class="answer-list">
				<!-- 구분선 : 답안 내용-->
					<c:forEach var="answer" items="${quiz.answerList }">
						<li id="answer-${answer.answerNum}"><label>${answer.content} <input type="checkbox" name="quiz-answers" value="${answer.answerNum}"> </label></li>
					</c:forEach>
				</ul>
				
				<!-- 풀이가 표시됨	-->				
				<div class="solution"></div>
				
				<button type="button" data-num="${quiz.quizNum}" class="btn-submit-answer">정답확인</button>
				</form>
				<!-- 구분선 -->
				<c:if test="${!status.last}">
					<hr>
				</c:if>
				</li>
				</c:forEach>
			</ul>

		</div>








	</main>
	<%@include file="/WEB-INF/views/include/footer.jsp"%>
</body>
</html>
