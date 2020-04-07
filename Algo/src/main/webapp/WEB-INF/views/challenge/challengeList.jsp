<!-- 도전과제 목록 화면 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.puercha.algo.challenge.controller.ChallengeController" %>
<%@ page import="com.puercha.algo.challenge.service.ChallengeManager" %>
<%@ page import="com.puercha.algo.challenge.vo.*" %>
<%@ page import="java.util.Map" %>
<%	
	pageContext.setAttribute("datas", request.getAttribute(ChallengeController.KEY_CHALLENGE_LIST_DATAS)); // datas라는 이름으로 객체 추가
 %>
<%-- <c:set var='datas' value="${ }"/> --%>
<c:set var="list" value="${datas[ChallengeManager.KEY_CHALLENGE_LIST]}"/>
<c:set var="pageInfo" value="${datas[ChallengeManager.KEY_PAGE_INFO ]}"/>
<!DOCTYPE html>
<html data-contextpath="${pageContext.request.contextPath}">
<head>
	<meta charset="UTF-8">
	<title>도전과제 목록</title>
	<%@include file="/WEB-INF/views/include/meta.jsp" %>
	<script type="text/javascript" src="<c:url value="/resources/js/chellenge-list.js"/>"></script>
	
</head>
<body>
	<%@include file="/WEB-INF/views/include/header.jsp" %>
	<main>
		
		<div>
			<h2>도전문제 목록</h2>
			
			<c:forEach items="${list}" var="challenge">
			<span>

				<!-- 도전문제 번호 -->
				${challenge.CNum}								
			</span>
			<span>
				<!-- 제목 -->
				<a href="${pageContext.request.contextPath}/challenge/${challenge.CNum}">${challenge.title }				
			</span>
			<span>
				<!-- 통과한 사람 수 -->
				${challenge.passNum }				
			</span>
			<span>
				<!-- 작성자 -->
				${challenge.username}				
			</span>
			<br>
			</c:forEach>			
		</div>
		<div>
			<%-- 검색어 초기화 --%>			
			<c:choose>
				<c:when test="${empty pageInfo.rc.searchType or empty pageInfo.rc.keyword }">
					<c:set var="searchParams" value=""/>
				</c:when>
				<c:otherwise>			
					<c:set var="searchParams" value="&type=${pageInfo.rc.searchType}&keyword=${pageInfo.rc.keyword}"/>
				</c:otherwise>
			</c:choose>
			
			<!-- 페이지 네비게이션 -->
			<c:if test="${pageInfo.prev}">
				<a href="${pageContext.request.contextPath}/challenge/list?page=1${searchParams}">처음</a>
				<a href="${pageContext.request.contextPath}/challenge/list?page=${pageInfo.startPage-1}${searchParams}">이전 페이지</a>
			</c:if>
			<c:forEach begin="${pageInfo.startPage}" end="${pageInfo.endPage}" step="1" var="num">
				<a href="${pageContext.request.contextPath}/challenge/list?page=${num}${searchParams}">${num}</a>
			</c:forEach>
			<c:if test="${pageInfo.next}">
				<a href="${pageContext.request.contextPath}/challenge/list?page=${pageInfo.endPage+1}${searchParams}">이전 페이지</a>
				<a href="${pageContext.request.contextPath}/challenge/list?page=${pageInfo.finalEndPage}${searchParams}">마지막</a>
			</c:if>
		</div>
		<div>
			<select id="search-type">
				<option value="T">제목 </option>
				<option value="C">내용 </option>
				<option value="TC">제목 + 내용</option>
				<option value="N">작성자이름</option>
			</select>
			<input type="text" id="input-search"/>
			<button type="button" id="btn-search-challenge" >검색</button>
		</div>
	</main>	
	<%@include file="/WEB-INF/views/include/footer.jsp" %>
</body>
</html>