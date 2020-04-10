<!-- 튜터신청서 처리 화면 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>튜터 등록</title>
	<%@include file="/WEB-INF/views/include/meta.jsp" %>
	<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/tutor-application-list.css"/>" />	
	<script type="text/javascript" src="<c:url value="/resources/js/tutor-application-list.js"/>"></script>
</head>
<body>
	<%@include file="/WEB-INF/views/include/header.jsp" %>
	<main>
		<!-- 이곳에 페이지의 컨텐츠가 담김 -->
		<h2>튜터 신청 목록</h2>
		<tr>
			<td>제목</td>
			<hr>
			<td>내용</td>
			<hr>
			<td>신청자</td>
			<hr>
			<td>제출날짜</td>
			<hr>
			<td>승인여부</td>
			<hr>
		</tr>
		<tr>
		<td></td>
		<hr>
		<td></td>
		<hr>
		<td></td>
		<hr>
		<td></td>
		<hr>
		<td></td>
		<hr>
		</tr>
			<td>
				<button type="button" class="aplBtn">승인</button>
				<button type="button" class="refBtn">거부</button>
			</td>
	</main>	
	<%@include file="/WEB-INF/views/include/footer.jsp" %>
</body>
</html>
