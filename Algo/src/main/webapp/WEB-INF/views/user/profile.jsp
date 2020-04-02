<!-- 회원정보화면 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>  
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>회 원 정 보 수 정</title>
	<%@include file="/WEB-INF/views/include/meta.jsp" %>
	<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/profile.css"/>" />	
	<script type="text/javascript" src="<c:url value="/resources/js/profile.js"/>"></script>
</head>
<body>
	<%@include file="/WEB-INF/views/include/header.jsp" %>
	<form:form modelAttribute="uvo"  action="${pageContext.request.contextPath }/user/profile" method="post" />
    <div class="mycontainer">
	<main>
		<!-- 이곳에 페이지의 컨텐츠가 담김 -->
		<h2>회원정보 수정</h2>
		<form:lable path="id">아이디</form:lable>
		<form:input type="text" path="email" readOnly="true" />
		<span class="errmsg" id="email_errmsg"></span>
		<form:errors path="email" cssClass="errmsg"></form:errors>
		
		<form:label path="pw">비밀번호</form:label>
		<form:input type="password" path="pw" />
		<span class="errmsg" id="pw_errmsg"></span>
		<form:errors path="pw" cssClass="errmsg"></form:errors>
		
		<form:label path="tel">전화번호</form:label>
		<form:input type="tel" path="tel" />
		<span class="errmsg" id="tel_errmsg"></span>
		<form:errors path="tel" cssClass="errmsg"></form:errors>
		
		<form:label path="username">닉네임</form:label>
		<form:input type="text" path="username" />
		<span class="errmsg" id="user_errmsg"></span>
		<form:errors path="username" cssClass="errmsg"></form:errors>
		
		<form:lable path="address">주소</form:lable>
		<form:input type="text" path="address" />
		<spen class="errmsg" id="address_errmsg"></spen>
		<form:errors path="address" cssClass="errmsg"></form:errors>
		
		<form:label path="gender">성별</form:label>
		<form:radiobuttons path="gender" items="${gender }" itemValue="code" itemLable="lable" />
		<span class="errmsg" id="gender_errmsg"></span>
		<form:errors path="gender" cssClass="errmsg"></form:errors>
		
		<form:lable path="birth">생년월일</form:lable>
		<form:input type="date" path="birth" />
		<span class="errmsg" id="birth_errmsg"></span>
		<form:errors path="birth" cssClass="errmsg"></form:errors>
		
		<button type="submit" id="modifyBtn">수 정 하 기</button>
	</main>	
	<%@include file="/WEB-INF/views/include/footer.jsp" %>
</body>
</html>
