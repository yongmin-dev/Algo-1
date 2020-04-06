<!-- 도전과제 편집화면 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html >
<html data-contextpath="${pageContext.request.contextPath}">
<head>
<meta charset="UTF-8">
<title>도전과제 편집</title>
<%@include file="/WEB-INF/views/include/meta.jsp" %>
	<style type="text/css">
		#case-list li.selected{
			background:lime;
		}
	
	</style>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/content-manager.js"></script>
</head>
<body>
	<%@include file="/WEB-INF/views/include/header.jsp" %>
	<main>
		<form:form modelAttribute="challenge" action="${pageContext.request.contextPath}/content/challenge/edit" method="POST" >
			<form:hidden path="userNum" value="${challenge.userNum}"/>
			<form:hidden path="cNum" value="${challenge.CNum}"/>
			<!-- 편집이 다 끝냈을 때 누르는 버튼 -->
			<form:button>편집 완료</form:button>
			<!-- 도전과제 제목 -->
			<form:label path="title">제목 </form:label><form:input path="title" value="${challenge.title}"/>
			<!-- 제한 메모리 -->
			<form:label path="limitMemory">제한메모리</form:label><form:input path="limitMemory" value="${challenge.limitMemory}"/>
			<!-- 제한 시간	-->
			<form:label path="limitTime">제한시간</form:label><form:input path="limitTime" value="${challenge.limitTime}"/>
<!-- 			<label><input type="radio" /></label>  -->
<!-- 			<label><input type="radio" /></label>  -->
			<!-- 문제내용 -->
			<form:textarea path="content" ></form:textarea>
			<!-- 도전과제 케이스 추가버튼 -->
			<button type="button" id="btn-new-case">추가</button>
			<!-- 도전과제 케이스 -->
			<ul id="case-list">
				
			</ul>
			<!-- 케이스 저장 버튼 -->
			<button id="btn-data-save">케이스 저장</button>
			<!-- 케이스의 인풋값 -->
			<label for="input-data-editor">	INPUT</label>			
			<textarea id="input-data-editor"></textarea>
			<!-- 케이스의 예상결과 -->
			<label for="expected-data-editor">EXPECTED</label>			
			<textarea id="expected-data-editor"></textarea>
		</form:form>
	</main>	
	<%@include file="/WEB-INF/views/include/footer.jsp" %>
</body>
</html>