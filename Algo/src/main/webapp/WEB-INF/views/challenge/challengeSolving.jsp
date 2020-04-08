<!-- 도전과제 풀기 화면  -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html data-contextpath="${pageContext.request.contextPath}">
<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
	<%@include file="/WEB-INF/views/include/meta.jsp" %>		
	<script type="text/javascript" src="<c:url value="/resources/js/challenge-solving.js"/>" ></script>
	<style>
	.CodeMirror{border:7px solid #f7f7f7;height:700px;}
	main{width:1280px; margin:0 auto;padding:20px 0;}
	.cont01{width:40%;float:left;border: 5px solid #f7f7f7;
    height: 665px;
    padding: 20px;}	
	#btn-submit-code{padding:10px 30px;cursor:pointer;float:right;}
	
	.cont01 ul li{padding:10px 0;font-size:16px;}
	.cont01 .content{height:300px;}
	.cont01 .result_cg{font-size:20px;}
	
	#after-submit button{padding:10px 30px;cursor:pointer;}
	
	</style>
</head>
<body>
	<%@include file="/WEB-INF/views/include/header.jsp" %>
	<main>
	
	<div class="cont01">
		<ul>
			<li>제목:${challenge.title}</li>
			<li>작성자:${challenge.username}</li>
			<li class="content"> 내용: <br><br> ${challenge.content}</li>
			<li>시간제한: ${challenge.limitTime}</li>
			<li>메모리제한: ${challenge.limitMemory}</li>
		</ul>
		<!-- 결과표시	-->
		
	<h3>결과 표시</h3>
		
		
	<div class="result_cg">
	
	<span id="result-msg"></span>
	<div id="after-submit"></div>
	</div>
	</div>

	<!-- 코드에디터 -->
	<textarea id="code-editor" rows="20" cols="40"></textarea>
	
	<!-- 전송버튼 -->
	<button id="btn-submit-code" data-context-path="${pageContext.request.contextPath}" data-cnum="${challenge.CNum}">제출</button>
	
	
	</main>	
	<%@include file="/WEB-INF/views/include/footer.jsp" %>
</body>
</html>