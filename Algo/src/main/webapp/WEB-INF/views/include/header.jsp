<!-- 모든페이지에서 사용할 헤더를 정의함 -->
<%@page import="com.puercha.algo.user.service.LoginService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.puercha.algo.user.*" %>
<%@ page import="com.puercha.algo.user.vo.*" %>
<div class="header clear" style="border-bottom:1px solid #555273;">
  <div class="header_inner clear">
  <h1 class="logo">
    <a href="${pageContext.request.contextPath}/"><img src="<c:url value="/resources/images/HatchfulExport-All (2)/logo_transparent.png"/>" alt=""></a>
  </h1>
  
<%
	UserVO user = (UserVO)request.getAttribute(LoginService.KEY_USER_INFO);
	if(user!=null)
		pageContext.setAttribute("userInfo", user);
 %>
 <c:set var="rootPath" value="${pageContext.request.contextPath}"></c:set>
<!-- 비로그인 -->
<div class="upper_menu">
	<c:choose>
		<c:when test="${empty userInfo}">
		    <ul>    
		      <li><a href="${pageContext.request.contextPath}/login/signing-in">로그인</a></li>
		      <li><a href="${pageContext.request.contextPath}/user/signing-up">회원가입</a></li>
		      <li><a href="#">ID/비밀번호 찾기</a></li>     
		    </ul>	
		</c:when>
		<c:otherwise>
			<ul>    
		      <li><a href="${pageContext.request.contextPath}/login/log-out">로그아웃</a></li>
		      <li><a href="${pageContext.request.contextPath}/user/updating">${userInfo.username}(${userInfo.type}) 마이페이지</a></li>		      
		    </ul>
		</c:otherwise>
	</c:choose>
	
<!-- 로그인 -->
    <!-- <ul>
      <li><a href="#">로그인</a></li>
      <li><a href="#">회원가입</a></li>
      <li><a href="#">ID/비밀번호 찾기</a></li>      
      <li><a href="#">마이페이지</a></li>      
    </ul> -->
  </div>
  <div class="bottom_menu clear">
    <div class="nav">
      <ul>
        <li><a href="#">ALGO소개</a></li>
        <li><a href="#">이론학습</a></li>
        <li><a href="#">마무리문제</a></li>
        <li><a href="#">도전과제</a></li>
        <li><a href="#">컨텐츠</a></li>
        <li><a href="#">게시판</a></li>
    </ul>
    </div>
  </div>
</div>
<div class="sub_menu">
  <div class="sub_menu_inner">
<%--   ${userInfo } --%>
<%-- 	userinfo= ${userInfo.type} --%>
    <ul>
      <li><a href="">ALGO소개</a></li>
      <li><a href="">· 소개</a></li>
      <li><a href="">· 연혁</a></li>      
    </ul>
    <ul>
      <li><a href="">이론학습</a></li>
      <li><a href="${rootPath}/learning/list">· 이론학습목록</a></li>
    </ul>
    <ul>
      <li><a href="">도전과제</a></li>
      <li><a href="${rootPath}/challenge/list">· 도전과제조회</a></li>      
    </ul>
    <c:if test="${!empty userInfo and userInfo.typeAsString ne 'S'}">
	    <ul>
	      <li><a href="">컨텐츠 관리</a></li>
	      <li><a href="${rootPath}/content/learning">· 이론학습 관리</a></li>
	      <li><a href="${rootPath}/content/challenge">· 도전과제 관리</a></li>
	    </ul>
    </c:if>
    
    <c:if test="${(!empty userInfo) and (!empty userInfo.type ) and (((userInfo.typeAsString) eq 'M') or userInfo.typeAsString eq 'm')}">
	    <ul>
	      <li><a href="">관리자</a></li>
	      <li><a href="${rootPath}/admin/application-list">· 튜터 신청서 관리</a></li>
	    </ul>
    </c:if>
    <ul>
      <li><a href="">게시판</a></li>      
      <li><a href="${rootPath}/board/list">· 질문게시판</a></li>
    </ul>
    
</div>
</div>
</div>


<!-- header end -->