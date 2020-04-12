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
	<style>
	
	main{width:980px;margin:0 auto;}
	main h2{padding:20px 0;}
	main ul li{display:inline-block;font-size:16px;padding:14px;}
	main ul li.title{}
	/* main ul > div:nth-child(2n-1){background:#EDEDFA;} */	
	main ul > div{border-bottom:2px solid #ededed;}
	main .paging a{font-size:16px;}
	#input-search{width:20%;}
	#btn-search-challenge{cursor:pointer;padding:6px 20px;}
	
	main ul li span{display:inline-block;font-size:14px;}

	
	
	</style>
</head>
<body>
	<%@include file="/WEB-INF/views/include/header.jsp" %>
	<main>
		<h2>도전문제 목록</h2>
		<ul>
		
			<li style="width:100%;">
			<span>번호</span>
			<span style="width:70%">제목</span>
			<span>맞춘사람</span>
			<span>작성자</span>
			</li>
		
		
			<c:forEach items="${list}" var="challenge">
				<div class="menu">
			<li class="1">
				<!-- 도전문제 번호 -->
				${challenge.CNum}								
			</li>
			<li class="2" style="width:70%">
				<!-- 제목 -->
				<a href="${pageContext.request.contextPath}/challenge/${challenge.CNum}">${challenge.title }				
			</li>
			<li class="3">
				<!-- 통과한 사람 수 -->
				${challenge.passNum }				
			</li>
			<li class="4">
				<!-- 작성자 -->
				${challenge.username}				
			</li>
			</div>
			</c:forEach>			

		</ul>
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
			<div class="paging">
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
		</div>
		<div style="text-align:center;padding:20px 0;">
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