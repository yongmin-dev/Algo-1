<!-- 단원내용 편집 화면 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html data-contextpath="${pageContext.request.contextPath}">
<head>
	<meta charset="UTF-8">
	<title>단원내용 편집</title>
	<%@include file="/WEB-INF/views/include/meta.jsp" %>	
	<style type="text/css">
		li.selected{background: #555273;color: #fff;}
	</style>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/content-manager.js"></script>
	<script type="text/javascript" src="<c:url value="/resources/js/learning-content-manager.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/js/unit-content-editor.js"/>"></script>
	
	<style>
	button{padding:5px 15px;cursor:pointer;margin:0 5px 0 0;}
	main{width:980px;margin:0 auto;padding:20px 0;}
	main textarea{height:200px;}
	.title_wrap{padding:20px 0;}
	#quiz-list{height:200px;overflow:auto;margin:0 0 15px 0;overflow-x:hidden;}
	#answer-list{height:200px;overflow:auto;margin:0 0 15px 0;overflow-x:hidden;}
	.content-list{
		border:1px gray dotted; 
	}
	.label{padding:0 0 10px 0;}
	.title-content-wrap {
	    border: 1px solid #e9e9e9;
	    background: #fff;	    
	    border-radius: 5px;
	    display: flex;
    }
	.title-content-wrap input{
		border:none;
	}
	.title-content-wrap chapter-depth{
		text-indent: 20px;	
		border-radius: 0px;	
		flex:3;
	}
	.title-content-wrap .title{
		flex:9;
	}
	</style>
	
	
	<script type="text/javascript">
		window.addEventListener('load',e=>{
			 ClassicEditor
             .create( document.querySelector( '#ckeditor' ) )
             .then( editor => {
                     console.log( editor );
             } )
             .catch( error => {
                     console.error( error );
             } );
		});
	</script>
</head>
<body>
	<%@include file="/WEB-INF/views/include/header.jsp" %>
	<main id="unit-editor-main" data-num="${unit.unitNum}" data-subjectnum="${unit.subjectNum }">
		<form:form modelAttribute="unit" method="POST"
			action="${pageContext.request.contextPath}/content/learning/unit/edit">
			<form:hidden path="unitNum"/>
			<form:hidden path="subjectNum"/>
<!-- 제출 -->
			<button type="submit" class="save_all">전체저장</button>
							
			<div class="title_wrap">
			<form:label path="title">제목</form:label>
				<div class="title-content-wrap">
				<!-- 단원번호-->
					<div class="chapter-depth">
						<form:hidden path="chapterDepth"/>
						<form:input disabled="true" type="text" value="${unit.chapterDepth}" path="chapterDepth" placeholder="단원 번호를 입력해주세요"/>			
					</div>
				
				<!-- 제목 -->
					<div class="title">
						<form:input type="text" path="title" value="${unit.title}" placeholder="제목을 입력해주세요~"/>
					</div>
				</div>
			</div>
				<!-- 내용 -->
				<form:label path="content">내용</form:label>
				<form:textarea path="content" id="ckeditor"></form:textarea>
<%-- 				<form:textarea path="content">${unit.content}</form:textarea> --%>
		</form:form>
		
		
			<div class="label">
		<!-- 문제 리스트 -->
		<label for="quiz-list">문제 리스트</label>
		<button type="button" id="btn-add-quiz">문제 추가</button>
		</div>
		<ul class="content-list" id="quiz-list">
		
		</ul>
	
		<!-- 문제 내용 편집		 -->
		<div class="label">
			<label for="quiz-content">문제 내용</label>
			<button id="btn-save-quiz-content" type="button">문제 내용 저장</button>
		</div>
			<textarea id="quiz-content"></textarea>
		
		<!-- 문제 답안		 -->
		<label for="answer-list">답안 리스트</label>
		<button id="btn-add-answer" type="button">답안 추가</button>
		<ul class="content-list" id="answer-list">
			
		</ul>
	</main>	
	<%@include file="/WEB-INF/views/include/footer.jsp" %>
</body>
</html>
