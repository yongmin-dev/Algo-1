<!-- 튜터신청서 처리 화면 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    
<!DOCTYPE html>
<html data-contextpath="${pageContext.request.contextPath}">
<head>
	<meta charset="UTF-8">
	<title>튜터 등록</title>
	<%@include file="/WEB-INF/views/include/meta.jsp" %>
	<script type="text/javascript" src="<c:url value="/resources/js/tutor-application-list.js"/>"></script>
	
	<style>
	main{width:980px;margin:0 auto;}
	main table th,td{padding:15px;}
	main table tr{border:1px solid #ededed;text-align:center;}
	main table .ok {text-align:center;}
	main table .ok button{padding:10px 20px;cursor:pointer;margin:0 5px 0 0;}
	</style>
</head>
<body>
	<%@include file="/WEB-INF/views/include/header.jsp" %>
	<main>
		<table id="application-table" >
			<tr>
				<th>제목</th>
				<th>내용</th>
				<th>신청자</th>
				<th>제출날짜</th>
				<th>승인여부</th>
			</tr>
			<c:forEach items="${datas.list }" var="application">
				<fmt:formatDate value="${application.createdAt}" pattern="yyyy/MM/dd" var="cdate" />
				
				<tr data-num="${application.applicationNum}">
				<td>${application.title }</td>
				<td>${application.content }</td>
				<td>${application.username }</td>
				<td>${cdate}</td>
				<td class="ok">
					<c:choose>
						<c:when test="${ application.approvalAsString eq 'p'}">
							<div class="group-btn">
								<button data-num="${applicaion.applicationNum}" class="btn-approval">승인</button>
								<button data-num="${applicaion.applicationNum}" class="btn-reject">거부</button>						
							</div>
						</c:when>
						<c:when test="${ application.approvalAsString eq 'r'}">
							<span>거절</span>						
						</c:when>
						<c:when test="${ application.approvalAsString eq 'a'}">
							<span>승인</span>												
						</c:when>
					</c:choose>
				</td>
				</tr>
			</c:forEach>
		</table>
	</main>	
	
<%-- 	${datas.list .get(0) } --%>
	<%@include file="/WEB-INF/views/include/footer.jsp" %>
</body>
</html>
