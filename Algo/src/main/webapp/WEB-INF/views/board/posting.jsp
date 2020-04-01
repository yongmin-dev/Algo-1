<!-- 게시글 쓰기 화면 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<%@include file="/WEB-INF/views/include/meta.jsp"%>
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/css/posting.css"/>" />
<script type="text/javascript"
	src="<c:url value="/resources/js/posting.js"/>"></script>
</head>
<body>
	<%@include file="/WEB-INF/views/include/header.jsp"%>
	<main>
		<!-- 이곳에 페이지의 컨텐츠가 담김 -->

		카테고리 정보 게시글카테고리번호
		<%-- 		${boardCategoryVO.cagetoryNum}
		게시글카테고리이름
		${boardCategoryVO.name} --%>

		회원 이름 ${boardPostVO.userName} 회원 번호 ${boardPostVO.userNum}



	</main>
	<%@include file="/WEB-INF/views/include/footer.jsp"%>
</body>
</html>
