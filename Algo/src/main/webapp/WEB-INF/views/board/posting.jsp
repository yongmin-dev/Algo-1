<!-- 게시글 쓰기 화면 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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

<br> 회원 이름: ${userVO.username} <br> 회원 번호: ${userVO.userNum}
<br>
		<form:select path="boardCategoryVO">
			<option value="0">=선택=</option>
			<form:options path="boardCategoryVO"
				items="${boardCategoryVO }" 
				itemValue="categoryNum"
				itemLabel="name" />
		</form:select>

		<br> 제목: ${boardPostVO.title } <br> 사용자 번호 :
		${boardPostVO.userNum } <br> 사용자 이름 : ${boardPostVO.userName } <br>
		생성일 : ${boardPostVO.createdAt } <br> 수정일 :
		${boardPostVO.updatedAt } <br> 조회수 : ${boardPostVO.hit } <br>
		내용 : ${boardPostVO.content }




	</main>
	<%@include file="/WEB-INF/views/include/footer.jsp"%>
</body>
</html>
