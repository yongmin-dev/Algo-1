<!-- 학습 컨텐츠 관리 화면 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html data-contextpath="${pageContext.request.contextPath}">
<head>
	<meta charset="UTF-8">
	<title>학습 컨텐츠 관리</title>
	<%@include file="/WEB-INF/views/include/meta.jsp" %>
	<style type="text/css">
	
	*{box-sizing:border-box;}
		#subject-list hidden{display: none;}
		#subject-list li.selected{background: #555273;color: #fff;}
		#unit-list li.selected{background: #555273;color: #fff;	}
		#subject-list li img{width:7em;	height:7em;}
		main{width:980px;margin:0 auto;padding:20px 0;overflow:hidden;}
		img,button{cursor:pointer;}
		button{padding:5px 15px;}
		.btn_wrap{float:right; }
 		.btn_wrap{margin-bottom:1em; } 
		
		#suject-list{width:50%;float:left;}
		#unit-list{width:50%;float:left;padding: 0 0 0 15px;}
		#subject-list li{padding:0.5em 1.0em;}
		#unit-list li{padding:1em}
		
		.cont_subject01{width:50%;float:left;}
		
		
		
		
		
	</style>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/content-manager.js"></script>
	<script type="text/javascript" src="<c:url value="/resources/js/learning-content-manager.js"/>"></script>
	
</head>
<body>
	<%@include file="/WEB-INF/views/include/header.jsp" %>
	<main>
	
		<div class="cont_subject01">
			<!-- 과목 추가 버튼 -->
			<button id="btn-new-subject">새과목 추가</button>
			<!-- 과목리스트 -->
			<ul class="content-list" class="content-list" id="subject-list">
			</ul>
		</div>
		<div class="btn_wrap">
		<!-- 단원 depth 왼쪽 -->
		<button id="btn-move-left">왼쪽</button>
		<!-- 단원 depth 오른쪽 -->
		<button id="btn-move-right">오른쪽</button>
		<!-- 단원 편집기 -->
		<button id="btn-unit-editor">편집기</button>
		<!-- 단원 추가 버튼 -->
		<button id="btn-new-unit">새 단원</button>
		
		</div>
		<!-- 단원리스트 -->
		<ul class="content-list" class="content-list" id="unit-list">
		</ul>
		<!-- 이곳에 페이지의 컨텐츠가 담김 -->
	</main>	
	<%@include file="/WEB-INF/views/include/footer.jsp" %>
</body>
</html>
