<!-- 게시글 보기 화면 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<%@include file="/WEB-INF/views/include/meta.jsp"%>
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/css/post-view.css"/>" />
	<script type="text/javascript"
	src="<c:url value="/resources/js/common.js"/>"></script>
<script type="text/javascript"
	src="<c:url value="/resources/js/board/post-view.js"/>"></script>
</head>
<body>
	<%@include file="/WEB-INF/views/include/header.jsp"%>

	<main>

		<h1 id="boardTitle">게시글 보기</h1>
		<form:form
			action="${pageContext.request.contextPath }/board/modifying/${postNum }/${returnPage }"
			enctype="multipart/form-data" method="post"
			modelAttribute="boardPostVO">
			<form:hidden path="postNum" />
			<div>
				<span>조회수 : ${boardPostVO.hit }</span>
				<span>리턴페이지 : ${returnPage }</span>
			</div>
			<div>
				<form:select path="category.categoryNum" disabled="true">
					<option value="0">=선택=</option>
					<form:options path="category.categoryNum"
						items="${boardCategoryVO }" itemValue="categoryNum"
						itemLabel="name"/>
				</form:select>
			</div>
			<div>
				<form:label path="title">제목</form:label>
				<form:input path="title" type="text" readOnly="true" />

			</div>
			<div>
				<form:label path="userName">작성자</form:label>
				<form:input type="text" path="userName" readOnly="true" />
			</div>
			<div>
				<form:label path="content">내용</form:label>
				<form:textarea path="content" readOnly="true"></form:textarea>

			</div>
			<div class="umode">
				<form:label path="files">첨부파일</form:label>
				<form:input type="file" multiple="multiple" path="files" />

			</div>

			<!-- 버튼들 -->
			<form:button class="rmode" id="replyBtn" data-returnPage="${returnPage }"
				data-postNum="${boardPostVO.postNum }">답글달기</form:button>
			<!-- 작성자 수정 삭제 가능 -->
			<c:if test="${userVO.userNum == boardPostVO.userNum }">
				<form:button class="rmode" id="modifyBtn">수정하기</form:button>
				<form:button class="rmode" id="deleteBtn"
					data-returnPage="${returnPage }"
					data-postNum="${boardPostVO.postNum }">삭제하기</form:button>
			</c:if>
			<!-- 작성자 수정 삭제 완료 -->
			<form:button class="umode" id="cancelBtn">취소하기</form:button>
			<form:button class="umode" id="saveBtn">저장하기</form:button>
			<form:button id="listBtn" data-returnPage="${returnPage }">목록보기</form:button>


		</form:form>
	</main>



	<!-- 댓글 -->
	<%@include file="/WEB-INF/views/board/comment.jsp"%>

	<%@include file="/WEB-INF/views/include/footer.jsp"%>
</body>
</html>
