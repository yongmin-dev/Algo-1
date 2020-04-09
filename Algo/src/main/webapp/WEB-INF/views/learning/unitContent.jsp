<!-- 단원 학습내용 화면 -->
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
	href="<c:url value="/resources/css/unit-content.css"/>" />
<script type="text/javascript"
	src="<c:url value="/resources/js/unit-content.js"/>"></script>
</head>
<body>
	<%@include file="/WEB-INF/views/include/header.jsp"%>
	<main>
		<!-- 이곳에 페이지의 컨텐츠가 담김 -->

		<div>
			<c:forEach var="unit" items="${unitList }">
				<h1>
					<a
						href="${pageContext.request.contextPath }/learning/unit/${subjectNum}/${unit.unitNum }">${unit.title }</a>
				</h1>
			</c:forEach>
		</div>
		<div>
			<div id="unitContent">
				<h1>단원 내용보기 : ${unitVO.content }</h1>
			</div>
			<div>
				<h1><a style="background-color : red"
					href="${pageContext.request.contextPath }/learning/quiz/${unitVO.unitNum }">
					마무리 문제 풀기</a></h1>
			</div>
		</div>



	</main>
	<%@include file="/WEB-INF/views/include/footer.jsp"%>
</body>
</html>


