<!-- 게시글 쓰기 화면 -->
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
	href="<c:url value="/resources/css/posting.css"/>" />
	<script type="text/javascript"
	src="<c:url value="/resources/js/common.js"/>"></script>
<script type="text/javascript"
	src="<c:url value="/resources/js/board/posting.js"/>"></script>
	<style>
		main{width:980px;margin:0 auto;}
	</style>
	
	
</head>
<body>
	<%@include file="/WEB-INF/views/include/header.jsp"%>
	<main>
		<!-- 이곳에 페이지의 컨텐츠가 담김 -->

		카테고리 정보 게시글카테고리번호 <br>
		 회원 이름: ${userVO.username} <br> 회원 번호:
		${userVO.userNum} <br>

		<form:form modelAttribute="boardPostVO" enctype = "multipart/form-data"
			action="${pageContext.request.contextPath}/board/posting/${returnPage }"
			method="post">
			<form:select path="category.categoryNum">
				<option value="0">=선택=</option>
				<form:options items="${boardCategoryVO}" 	itemValue="categoryNum" itemLabel="name" />
			</form:select>
			<div>
				<form:label path="title">제목</form:label>
				<form:input type="text" path="title" />
			</div>
			<div>
				<form:label path="userNum">작성자번호</form:label>
				<form:input type="text" path="userNum" readOnly="readOnly"
					value="${userVO.userNum }" />
			</div>
			<div>
				<form:label path="userName">작성자</form:label>
				<form:input type="text" path="userName" readOnly="readOnly"
					value="${userVO.username }" />

			</div>

			<div>
				<form:label path="content">내용</form:label>
				<form:textarea path="content" rows="10"></form:textarea>
			</div>
			<div>
				<form:label path="files">첨부파일</form:label>
				<form:input type="file" multiple="multiple"  path="files" />
			</div>
			<div>
				<form:button id="writeBtn">등록</form:button>
				<form:button id="cancelBtn">취소</form:button>
				<form:button id="listBtn" data-returnPage="${returnPage }">목록</form:button>
			</div>

		</form:form>



	</main>
	<%@include file="/WEB-INF/views/include/footer.jsp"%>
</body>
</html>
