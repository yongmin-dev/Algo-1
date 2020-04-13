<!-- 과목목록 화면 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>과목목록</title>
<%@include file="/WEB-INF/views/include/meta.jsp"%>
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/css/subject-list.css"/>" />
<script type="text/javascript"
	src="<c:url value="/resources/js/subject-list.js"/>"></script>
	
	<style>
	.container{background:#fff;height:auto;width:980px;margin:0 auto;}
	.container div a {font-size:20px;}
	.progress-rate{font-size:13px;}
	.subject_wrap{overflow:hidden;}
	.subject_cont{width:50%;float:left;}
	
	</style>
	
	
	
</head>
<body>
	<%@include file="/WEB-INF/views/include/header.jsp"%> 
	<div class="container">
		<!-- 과목 리스트가 표시 됨 -->
		<div class="subject_wrap">
<%-- 			${datas } --%>
			<c:forEach var="subject" items="${datas.list }">
				<fmt:formatDate value="${subject.createdAt }"
					pattern="yyyy/MM/dd HH:mm" var="cdate" />
				<fmt:formatNumber var="progressRate" value="${subject.progressRate}" type="NUMBER" maxFractionDigits="1"/>
				<div class="subject_cont">
					<div>
						<a
							href="${pageContext.request.contextPath }/learning/subject/${subject.subjectNum }">
							<!-- 과목 이미지 -->
							<img src="${pageContext.request.contextPath }/learning/subject/image/${subject.subjectNum }"width="120px" height="120px">
							<!-- 과목 제목-->
							<span class="title"><strong class="title">${subject.title }</strong></span>
							<c:if test="${!empty userInfo }">
								<span class="progress-rate">${progressRate }%</span>
							</c:if>
						</a>
					</div>
				</div>
			</c:forEach>

		</div>
		<!-- 과목 페이징 -->
		<div class="paging">
			<i class="fas fa-chevron-left"></i>
			<div id="paging">
				<c:set var="search" value="/${pm.rc.searchType}/${pm.rc.keyword}"></c:set>

				<c:if test="${empty pm.rc.searchType or empty pm.rc.keyword }">
					<c:set var="search" value=""></c:set>
				</c:if>
				<!-- 처음페이지 / 이전페이지 이동 -->
				<c:if test="${pm.prev }">
					<a href="${pageContext.request.contextPath }/learning/list/1/">처음</a>
					<a
						href="${pageContext.request.contextPath }/learning/list/${pm.startPage-1}${search}">이전</a>
				</c:if>
				<c:forEach var="pageNum" begin="${pm.startPage }"
					end="${pm.endPage }">
					<!-- 현재페이지와 요청페이지가 다르면 -->
					<c:if test="${pm.rc.reqPage != pageNum }">
						<a
							href="${pageContext.request.contextPath }/learning/list/${pageNum }${search}"
							class="off">${pageNum }</a>
					</c:if>
					<!-- 현재페이지와 요청페이지가 같으면 -->
					<c:if test="${pm.rc.reqPage == pageNum }">
						<a
							href="${pageContext.request.contextPath }/learning/list/${pageNum }${search}"
							class="on">${pageNum }</a>
					</c:if>
				</c:forEach>
				<!-- 다음페이지 / 최종페이지 이동 -->
				<c:if test="${pm.next }">
					<a
						href="${contextRoot }/learning/list/${pm.endPage+1}/${pm.rc.searchType}/${pm.rc.keyword}">다음</a>
					<a
						href="${contextRoot }/learning/list/${pm.finalEndPage}/${pm.rc.searchType}/${pm.rc.keyword}">최종</a>
				</c:if>
			</div>
		</div>


	</div>
	<%@include file="/WEB-INF/views/include/footer.jsp"%>







</body>
</html>

