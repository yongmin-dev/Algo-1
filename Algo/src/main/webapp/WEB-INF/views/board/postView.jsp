<!-- 게시글 보기 화면 -->
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
	href="<c:url value="/resources/css/post-view.css"/>" />
<script type="text/javascript"
	src="<c:url value="/resources/js/post-view.js"/>"></script>
</head>
<body>
	<%@include file="/WEB-INF/views/include/header.jsp"%>
	<main>
		<!-- 이곳에 페이지의 컨텐츠가 담김 -->

		<!-- 게시글번호 -->
		${boardPostVO.postNum}

<%-- 		<!-- 게시글카테고리번호 <form:select> -->
		${boardCategoryVO.cagetoryNum}

		<!-- 게시글카테고리이름 <form:select>-->
		${boardCategoryVO.name}
 --%>
		<!-- 유저번호 -->
		${boardPostVO.userNum}
		
		<!-- 유저이름 -->		
		 ${boardPostVO.userName}
		 <!-- 생성일 -->
		${boardPostVO.createdAt}
		
		<!-- 수정일 -->		
		${boardPostVO.updatedAt}
		<!-- 조회수 -->
		 ${boardPostVO.hit}
		 
		 <!-- 내용 -->
		${boardPostVO.content}
		
		<!-- @attachmentVOs : 첨부파일  -->
		<c:if test="${!empty attachmentVOs }">
			<c:forEach var="attachment" items="attachmentVOs" >			
			${attachment.fid}
			${attachment.fname}
			${attachment.fsize/1000}kb
		</c:forEach>

		</c:if>
	</main>
	<!-- 댓글 -->
	<%@include file="/WEB-INF/views/board/comment.jsp"%>

	<%@include file="/WEB-INF/views/include/footer.jsp"%>
</body>
</html>
