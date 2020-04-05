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
			<form:button>편집 완료</form:button>
			<form:label path="title">제목 </form:label><form:input path="title" value="${challenge.title}"/>
			<form:label path="limitMemory">제한메모리</form:label><form:input path="limitMemory" value="${challenge.limitMemory}"/>
			<form:label path="limitTime">제한시간</form:label><form:input path="limitTime" value="${challenge.limitTime}"/>
<!-- 			<label><input type="radio" /></label>  -->
<!-- 			<label><input type="radio" /></label>  -->
			<form:textarea path="content" ></form:textarea>
			<button type="button" id="btn-new-challenge">추가</button>
			<ul id="case-list">
				
			</ul>
			<button id="btn-data-save">케이스 저장</button>
			<label for="input-data-editor">	INPUT</label>
			<textarea id="input-data-editor"></textarea>
			<label for="expected-data-editor">EXPECTED</label>			
			<textarea id="expected-data-editor"></textarea>
		</form:form>
	</main>	
	<%@include file="/WEB-INF/views/include/footer.jsp" %>
</body>
</html>