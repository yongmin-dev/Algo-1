<!-- 회원가입화면 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입페이지</title>
<%@include file="/WEB-INF/views/include/meta.jsp"%>
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/css/signup.css"/>" />
<script type="text/javascript"
	src="<c:url value="/resources/js/signup.js"/>"></script>
	
	<style>
	main{width:480px;margin:0 auto;margin-top: 150px;}
	h2{text-align:center;}
	#joinBtn{cursor:pointer;padding:10px 20px;width:100%;text-align:center;}
	</style>
	
	
</head>
<body>
	<%@include file="/WEB-INF/views/include/header.jsp"%>
	<main>
		<form:form action="${pageContext.request.contextPath }/user/sign-up"	modelAttribute="uvo" method="post">
			<!-- 이곳에 페이지의 컨텐츠가 담김 -->
			<h2>회원가입</h2>
			<form:label path="email">이메일</form:label>
			<form:input type="text" path="email" />
			<span class="errmsg" id="email_errmsg"></span>
			<form:errors path="email" cssClass="errmsg"></form:errors>

			<form:label path="pw">비밀번호</form:label>
			<form:input type="password" path="pw" />
			<span class="errmsg" id="pw_errmsg"></span>
			<form:errors path="pw" cssClass="errmsg"></form:errors>

			<label for="pwChk">비밀번호 재확인</label>
			<input type="password" id="pwChk" />
			<span class="errmsg" id="pwChk_errmsg"></span>

			<form:label path="tel">전화번호</form:label>
			<form:input type="tel" path="tel" />
			<span class="errmsg" id="tel_errmsg"></span>
			<form:errors path="tel" cssClass="errmsg"></form:errors>

			<form:label path="username">닉네임</form:label>
			<form:input type="text" path="username" />
			<span class="errmsg" id="usernick_errmsg"></span>
			<form:errors path="username" cssClass="errmsg"></form:errors>

			<form:label path="address">주소</form:label>
			<form:input type="text" path="address" />
			<spen class="errmsg" id="address_errmsg"></spen>
			<form:errors path="address" cssClass="errmsg"></form:errors>

			<form:label path="gender">성별</form:label> <br />
			<form:radiobuttons path="gender" items="${gender }" itemValue="code"
				itemLabel="label" />
			<span class="errmsg" id="gender_errmsg"></span>
			<form:errors path="gender" cssClass="errmsg"></form:errors> <br />

			<form:label path="birth">생년월일</form:label>
			<form:input type="date" path="birth" />
			<span class="errmsg" id="birth_errmsg"></span>
			<form:errors path="birth" cssClass="errmsg"></form:errors>
			<button type="submit" id="joinBtn">가 입 하 기</button> 
		</form:form>
	</main>
	<%@include file="/WEB-INF/views/include/footer.jsp"%>
</body>
</html>
