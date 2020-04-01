<!-- 게시글 보기 화면 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
	<%@include file="/WEB-INF/views/include/meta.jsp" %>
	<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/post-view.css"/>" />	
	<script type="text/javascript" src="<c:url value="/resources/js/post-view.js"/>"></script>
</head>
<body>
	<%@include file="/WEB-INF/views/include/header.jsp" %>
	<main>
		<!-- 이곳에 페이지의 컨텐츠가 담김 -->
		
		${boardPostVO.postNum}
		${boardPostVO.category}		
		${boardCategoryVO}
		${boardPostVO.userNum}
		${boardPostVO.userName}
		${boardPostVO.createdAt}
		${boardPostVO.updatedAt}
		${boardPostVO.hit}
		${boardPostVO.content}
		${boardPostVO.files}
		${boardPostVO.hit}
		${attachmentVO.fid}
		${attachmentVO.fname}
		${attachmentVO.fsize}
		
	</main>	
	<!-- 댓글 -->
	<%@include file="/WEB-INF/views/board/comment.jsp" %>
	
	<%@include file="/WEB-INF/views/include/footer.jsp" %>
</body>
</html>
