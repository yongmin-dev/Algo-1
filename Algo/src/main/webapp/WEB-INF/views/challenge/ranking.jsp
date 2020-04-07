<!-- 랭킹 조회 화면 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html data-contextpath="${pageContext.request.contextPath}">
<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
	<%@include file="/WEB-INF/views/include/meta.jsp" %>		
	<style type="text/css">
		/* 내꺼 중에 제일 높은 것 */
		.my-rank{
			background-color: #aaffcc
		}
		/* 내꺼인건 다 표시		 */
		.my-result{
			background-color: #aaffee
		}
	</style>
	<script type="text/javascript" src="<c:url value="/resources/js/ranking.js"/>"></script>
</head>
<body>
	<%@include file="/WEB-INF/views/include/header.jsp" %>
	<main>		 
		<!-- 검색하는 페이지보다 내 등수가 상위일 경우 -->
		<table>
		<c:if test="${!empty myBestResult and !empty list and list.size()>0 and myBestResult.rankNum < list.get(0).rankNum }">
			<tr class="my-rank">
				<!-- 랭크 -->
				<td>${myBestResult.rankNum}</td>
				<!-- 제출자 -->
				<td>${myBestResult.username}</td>
				<!-- 사용 메모리-->
				<td>${myBestResult.usedMemory}</td>
				<!-- 사용 시간 -->
				<td>${myBestResult.processingTime}</td>
				<!-- 제출 날짜 -->
				<td>${myBestResult.createdAt}</td>
				<!-- 결과 설명 -->
				<td>${myBestResult.resultComment}</td>
			</tr>
		</c:if>
		<c:forEach items="${list}" var="result">
			<c:choose>
				<c:when test="${result.resultNum eq myBestResult.resultNum}">
					<tr class="my-rank">			
				</c:when>
				<c:when test="${result.userNum eq myBestResult.userNum}">
					<tr class="my-result">			
				</c:when>				
				<c:otherwise>
					<tr>
				</c:otherwise>
			</c:choose>
					<!-- 랭크 -->
				<td>${result.rankNum}</td>
					<!-- 제출자 -->
				<td>${result.username}</td>
					<!-- 사용 메모리-->
				<td>${result.usedMemory}</td>
					<!-- 사용 시간 -->
				<td>${result.processingTime}</td>
					<!-- 제출 날짜 -->
				<td>${result.createdAt}</td>
					<!-- 결과 설명 -->
				<td>${result.resultComment}</td>
			</tr>
			</c:forEach>
			<!-- 검색하는 페이지보다 내 등수가 하위일 경우 -->
			<c:if test="${!empty myBestResult and !empty list and list.size()>0 and myBestResult.rankNum > list.get(list.size()-1).rankNum  }">
				<tr class="my-rank">
					<!-- 랭크 -->
					<td>${myBestResult.rankNum}</td>
					<!-- 제출자 -->
					<td>${myBestResult.username}</td>
					<!-- 사용 메모리-->
					<td>${myBestResult.usedMemory}</td>
					<!-- 사용 시간 -->
					<td>${myBestResult.processingTime}</td>
					<!-- 제출 날짜 -->
					<td>${myBestResult.createdAt}</td>
					<!-- 결과 설명 -->
					<td>${myBestResult.resultComment}</td>
				</tr>
			</c:if>
			</table>
			
			<div>			
			
			<!-- 페이지 네비게이션 -->
			<c:if test="${pageInfo.prev}">
				<a href="${pageContext.request.contextPath}/ranking/${cNum}/1?type=${type}">처음</a>
				<a href="${pageContext.request.contextPath}/ranking/${cNum}/${pageInfo.startPage-1}?type=${type}">이전 페이지</a>
			</c:if>
			<c:forEach begin="${pageInfo.startPage}" end="${pageInfo.endPage}" step="1" var="num">
				<c:choose>
					<c:when test="${curPage eq num}">
					<!-- 현재 페이지 -->
						<a href="${pageContext.request.contextPath}/ranking/${cNum}/${num}?type=${type}">${num}</a>					
					</c:when>
					<c:otherwise>
					<!-- 현재 페이지가 아닌 나머지 페이지 -->
						<a href="${pageContext.request.contextPath}/ranking/${cNum}/${num}?type=${type}">${num}</a>
					</c:otherwise>
				</c:choose>
			</c:forEach>
			<c:if test="${pageInfo.next}">
				<a href="${pageContext.request.contextPath}/ranking/${cNum}/${pageInfo.endPage+1}?type=${type}">이전 페이지</a>
				<a href="${pageContext.request.contextPath}/ranking/${cNum}/${pageInfo.finalEndPage}?type=${type}">마지막</a>
			</c:if>
			</div>
			<div>
			<label><input name="type" type="radio" value="T" ${(type =='T') ? 'checked': ' '} />시간</label>
			<label><input name="type" type="radio" value="M" ${(type =='M') ? 'checked': ' '} />메모리</label>
			<button id="btn-search-rank" data-cnum="${cNum}">검색</button>
			</div> 
	</main>	
	<%@include file="/WEB-INF/views/include/footer.jsp" %>
</body>
</html>
