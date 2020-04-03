<!-- 도전과제 풀기 화면  -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
	<%@include file="/WEB-INF/views/include/meta.jsp" %>		
	<script type="text/javascript" >
		// init
		window.addEventListener("load",(e)=>{
			const submitCodeBtn = document.getElementById('btn-submit-code');
			const codeEditor = document.getElementById('code-editor');

			// 전송 버튼 클릭
			if(submitCodeBtn){
				submitCodeBtn.addEventListener("click",(e)=>{
					const contextPath = e.currentTarget.getAttribute("data-context-path"); 
					const cNum = e.currentTarget.getAttribute("data-cnum");
					console.log("contextPath:",contextPath ); 
					console.log("cNum:",cNum ); 
					var xhr = new XMLHttpRequest();
					xhr.open('POST',contextPath+"/challenge/code/"+cNum); 		
					const formdata = new FormData();
					formdata.append('code',codeEditor.value);
					// 전송 완료 시 
					xhr.addEventListener('load',(e)=>{
						console.log(e.currentTarget.response);
					});
					//
					xhr.send(formdata);
				});				
			}
			
		});

	</script>
	
</head>
<body>
	<%@include file="/WEB-INF/views/include/header.jsp" %>
	<main>
	<!-- 제목 -->
	${challenge.title}<br>
	<!-- 제목 -->
	${challenge.username}<br>
	<!-- 내용 -->
	${challenge.content}<br>
	<!-- 제한시간 -->
	${challenge.limitTime}<br>
	<!-- 제한메모리 -->
	${challenge.limitMemory}<br>
	<!-- 코드에디터 -->
	<textarea id="code-editor" rows="20" cols="40"></textarea>
	<button id="btn-submit-code" data-context-path="${pageContext.request.contextPath}" data-cnum="${challenge.CNum}">제출</button>
	</main>	
	<%@include file="/WEB-INF/views/include/footer.jsp" %>
</body>
</html>