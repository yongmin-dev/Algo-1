<!-- 게시판 화면 -->
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
	href="<c:url value="/resources/css/board.css"/>" />
<script type="text/javascript"
	src="<c:url value="/resources/js/board.js"/>"></script>
</head>
<body>
	<%@include file="/WEB-INF/views/include/header.jsp"%>
	<main>
		<!-- 이곳에 페이지의 컨텐츠가 담김 -->

		<div>
			<table>
				<tr>
					<th>번호</th>
					<th>분류</th>
					<th>제목</th>
					<th>작성자</th>
					<th>작성일</th>
					<th>조회수</th>
				</tr>
				<c:forEach var="record" items="${list }">
					<fmt:formatDate value="${record.createdAt }"
						pattern="yyyy/MM/dd HH:mm" var="cdate" />
					<tr>
						<td>${record.postNum }</td>
						<td>${record.category.categoryNum }</td>
						<td><c:forEach begin="1" end="${record.indent }">&nbsp;&nbsp;</c:forEach>
							<c:if test="${record.indent > 0 }">
							</c:if> <a
							href="${pageContext.request.contextPath }/board/post/${record.postNum }/${pm.rc.reqPage}">
								${record.title }</a>
						<td>${record.userName }(${record.userNum })</td>
						<td>${record.createdAt }</td>
						<td>${record.hit }</td>
					</tr>
				</c:forEach>
			</table>
		</div>


	</main>
	<%@include file="/WEB-INF/views/include/footer.jsp"%>
</body>
</html>
