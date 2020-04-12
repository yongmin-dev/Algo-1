<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html data-contextpath="${pageContext.request.contextPath}">
<head>
	<title>메인화면</title>
	<meta charset="UTF-8">
	<!-- 절대경로 사용 -->
	<%@include file="/WEB-INF/views/include/meta.jsp" %>	
	<style>	
	.cont_left,.cont_right,.cont_center{width:33%;float:left;}
	.container ul li {display:inline-block;}
	h3{text-align:center;}
	
	span{display:inline-block;}
	.cont_left_inner,.cont_right_inner,.cont_center_inner{padding:30px;}
	.cont_left ul li{font-size:16px;text-align:center;}
	.cont_left ul li a{display:inline-block;padding:18px 0 0 0;}
	.cont_left ul li a:hover{font-weight:bold;}
	
	.cont_right_inner{font-size:16px;}
	.cont_right_inner ul li{display:inline-block;padding:18px 0 0 0;}
	.cont_right_inner ul li a:hover{font-weight:bold;}
	
	.cont_center_inner ul{padding:18px 0 0 0;text-align:center;}
	
	
	.container{height:auto;overflow:hidden;margin-bottom:30px;background:#fff;}
	.container h3{padding:0 0 10px 0;}
	
	</style>
	
	
</head>
<body>
<%@include file="/WEB-INF/views/include/header.jsp" %>
<!-- 슬라이드 start-->
<div class="slider" style="height:500px;">
<img src="<c:url value="/resources/images/main_img.jpg"/>" alt="" />
<img src="<c:url value="/resources/images/main_img2.jpg"/>" alt="" />
<img src="<c:url value="/resources/images/main_img3.png"/>" alt="" />
</div> 
<!-- 슬라이드 end-->
	<main class="container">
		<div class="cont_left">
			<div class="cont_left_inner">
				<h3>인기 문제(통과수/도전자수)</h3>
				<ul>
					<c:forEach items="${hotChallenges}" var="challenge">
						<li style="width:100%;">
							<span><a href="#">${challenge.title }</a></span>
							<span style="font-weight:bold;font-size:16px;color:#88dd99">(${challenge.passer}</span>
							<span style="font-weight:bold;font-size:16px;color:#aabbcc">/${challenge.challenger})</span>
						</li>
					
					
					</c:forEach>
					
																
				</ul>
			</div>
		</div>
		<div class="cont_center">
			<div class="cont_center_inner">
				<h3>랭킹 순위</h3>
					<c:forEach items="${rankers}" var="ranker" varStatus="status">
						<ul>
							<li><strong style="font-size:14px">NO.${status.count}</strong></li>
							<li><strong style="font-size:16px">${ranker.username}</strong><small>(${ranker.userNum}) </small></li>									
							<li>${ranker.passedNum}개</li>									
						</ul>
					
					</c:forEach>								
			</div>
		</div>
		<div class="cont_right">
			<div class="cont_right_inner">
				<h3>질문 게시판</h3>
					<c:forEach items="${ boardListDatas.list}" var="boardItem" end="5">
						<ul>
							<fmt:formatDate value="${boardItem.createdAt}" pattern="yyyy/MM/dd" var="cdate" />
						
							<li><a href="#"> ${boardItem.title }  </a> </li>						
							<li style="float:right;">${cdate}</li>										
						</ul>
					</c:forEach>				
														
			</div>
		</div>
		
	<!-- 	<div class="ad" style="clear:both;"><img src="https://homepages.cae.wisc.edu/~ece533/images/cat.png" alt="" /></div> -->
		
	</main>	
<%@include file="/WEB-INF/views/include/footer.jsp" %>
</body>
</html>
