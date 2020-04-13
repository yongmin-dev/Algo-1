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
<%-- 	<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/profile.css"/>" />	 --%>
<%-- 	<script type="text/javascript" src="<c:url value="/resources/js/profile.js"/>"></script> --%>
	<style>
	h2{text-align:center;padding:100px 0 0 0;}
	main{width:480px;margin:0 auto;}
	#modifyBtn{width:100%;text-align:center;cursor:pointer;padding:10px 0;}
	
	#applyTutor{padding:10px 20px; cursor:pointer;float:right;margin:10px 0 0 0;}
	
	</style>
	<script type="text/javascript">
	window.addEventListener('load',e=>{
		const applyTutorBtn = document.getElementById('applyTutor');
		if(applyTutorBtn){
			applyTutorBtn.addEventListener('load',e=>{
				location.href = '${pageContext.request.contextPath }/user/applying'
			});
		}
	})
	</script>
</head>
<body>
	<%@include file="/WEB-INF/views/include/header.jsp" %>
	<main>
		<form:form modelAttribute="uvo"  action="${pageContext.request.contextPath }/user/update" method="post" >    
			<!-- 이곳에 페이지의 컨텐츠가 담김 -->
			<form:hidden path="userNum"/>
			<h2>회원정보 수정</h2>
			<form:label path="email">이메일</form:label>
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
			
			<form:label path="username">사용자이름</form:label>
			<form:input type="text" path="username" />
			<span class="errmsg" id="user_errmsg"></span>
			<form:errors path="username" cssClass="errmsg"></form:errors>
			
			<form:label path="address">주소</form:label>
			<form:input type="text" path="address" />
			<span class="errmsg" id="address_errmsg"></span>
			<form:errors path="address" cssClass="errmsg"></form:errors>
			
			<form:label path="gender">성별</form:label> <br>
			<form:radiobuttons path="gender" items="${gender}" itemValue="code" itemLabel ="label"  />
			<span class="errmsg" id="gender_errmsg"></span>
			<form:errors path="gender" cssClass="errmsg"></form:errors>
			
			<br>	
			<form:label path="birth">생년월일</form:label>
			<form:input type="date" path="birth" />
			<span class="errmsg" id="birth_errmsg"></span>
			<form:errors path="birth" cssClass="errmsg"></form:errors>
			
			<button type="submit" id="modifyBtn">수 정 하 기</button>
			<button type="button" id="applyTutor">튜터 신청하기</button>
		</form:form>
	</main>	
	<%@include file="/WEB-INF/views/include/footer.jsp" %>
</body>
</html>
