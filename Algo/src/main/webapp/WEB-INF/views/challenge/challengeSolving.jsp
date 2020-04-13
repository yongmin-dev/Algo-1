<!-- 도전과제 풀기 화면  -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html data-contextpath="${pageContext.request.contextPath}">
<head>
	<meta charset="UTF-8">
	<title>도전과제:${challenge.title}</title>
	<%@include file="/WEB-INF/views/include/meta.jsp" %>		
	<script type="text/javascript" src="<c:url value="/resources/js/challenge-solving.js"/>" ></script>
	<style>
	.CodeMirror{border:7px solid #f7f7f7;height:700px;}
	main{width:1280px; margin:0 auto;padding:20px 0;}
	.cont01{width:40%;float:left;border: 5px solid #f7f7f7;
    height: 665px;
    padding: 20px;}	
	#btn-submit-code{padding:10px 30px;cursor:pointer;}
	
	.cont01 ul li{padding:10px 0;font-size:16px;}
	.cont01 .content{height:300px;}
	.cont01 .result_cg{font-size:40px;}
	
	#after-submit button{padding:10px 30px;cursor:pointer;}
	
	</style>
</head>
<body>
	<%@include file="/WEB-INF/views/include/header.jsp" %>
	<main>
	
	<div class="cont01">
		<ul>
			<li><h2>${challenge.title}</h2></li>
			<li><strong>작성자 : </strong>${challenge.username}</li>
			<li><strong>정답자(도전자) : </strong>${challenge.passNum}(${challenge.challenger})</li>			
			<li><strong>제한 시간:</strong> ${challenge.limitTime} ms</li>
			<li><strong>메모리제한:</strong> ${challenge.limitMemory} byte</li>
			<li class="content"> <strong>내용:</strong><br><br> <div class="content">${challenge.content}</div></li>
		</ul>
		<!-- 결과표시	-->
		
	<h3>결과 표시</h3>
		
		
	<div class="result_cg">
	
	<span id="result-msg"></span>
	<div id="after-submit"></div>
	</div>
	</div>

	<!-- 코드에디터 -->
	<textarea id="code-editor" rows="20" cols="40">
		class Main{ // 반드시 클래스 명은 Main이어야 합니다.
			public static void main(String[] args) {
				//이 곳에 내용을 작성하세요~
			}
		}
	</textarea>
	
	<!-- 전송버튼 -->
	<div class="row" style="width:100%; display:flex;justify-content: flex-end;">
		<button id="btn-submit-code" data-context-path="${pageContext.request.contextPath}" data-cnum="${challenge.CNum}">제출</button>
	</div>
	
	
	</main>	
	<%@include file="/WEB-INF/views/include/footer.jsp" %>
</body>
</html>