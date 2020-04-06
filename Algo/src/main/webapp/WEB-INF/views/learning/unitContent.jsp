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
			<table>
				<tr>
					<th>단원명</th>
					<th>단원명</th>
					<th>내용</th>
					<th>단원제목</th>
					<th>챕터뎁스</th>

				</tr>

				<c:forEach var="unit" items="${unitList }">
					<fmt:formatDate value="${unit.createdAt }"
						pattern="yyyy/MM/dd HH:mm" var="cdate" />
					<tr>
						<td>${unit.unitNum }</td>
						<td>${unit.content }</td>
						<td><a
							href="${pageContext.request.contextPath }/learning/unit/${unit.unitNum }">
								${unit.title }</a>
						<td>${cdate }</td>
					</tr>
				</c:forEach>
			</table>
		</div>

		<!-- 단워 내용보기는 unitVO로 불러옴 -->
		단원 내용보기 : ${unitVO.title } <a
			href="${pageContext.request.contextPath }/learning/quiz/${unitVO.unitNum }">
			마무리 문제 풀기</a>
			
			
			

	</main>
	<%@include file="/WEB-INF/views/include/footer.jsp"%>
</body>
</html>


