<!-- 마무리 문제 풀기 화면 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html data-contextpath="${pageContext.request.contextPath}">
<head>
<meta charset="UTF-8">
<title>마무리 문제 풀기</title>
<%@include file="/WEB-INF/views/include/meta.jsp"%>
<style type="text/css">
	#quiz-list > li.passed .quiz-title{
		background: #11ff33;
	}
	#quiz-list > li.failed .quiz-title{
		background: #ffbbbb;
	}
	ul.answer-list > li.wrong-answer{
		background #ff1111;
	}
	ul.answer-list > li.correct-answer{
		/* background:#11ffbb; */
	}
	#quiz-nav-list{
		display:flex;
		justify-content: center;
	}
	#quiz-nav-list > left: ;i.quiz-nav{
		width:auto;
		padding:1em;
	}
 	#quiz-nav-list > li.passed ::before{
 	/* 	background:#11ffbb; */
	 	content:'\2713';
	  display:inline-block;
	  color:#20ff27;
	  padding:0 6px 0 0;
 	
 	}
 	#quiz-list > li.passed .quiz-title:after{
 		content:" 정답!";
 	}
 	#quiz-list > li.failed .quiz-title:after{
 		content:" 오답!";
 	}
	
	main{width:980px;margin:0 auto;}
	main h2{padding:15px 0;}
	button{cursor:pointer;padding:10px 20px;float:right;}
	.quiz-list{overflow:hidden;}
	.quiz-item{width:50%;float:left;box-sizing:border-box;padding:10px;}
	
	#quiz-nav-list{
		padding:1em;
	}
	#quiz-list li.quiz-item{
		display:none;
	}
	#quiz-list li.quiz-item.selected{
		display:block;
	}
	
	
	/* 
	#quiz-nav-list{position:absolute;width:100%;}
	#quiz-list{overflow:hidden;}
	#quiz-list li{width:100%;height:500px;}
	.quiz_container{position:relative;} */
	
	#quiz-nav-list:hover{cursor:pointer;}
	#quiz-nav-list li{width: 25%!important;text-align: center;}
	#quiz-nav-list li:first-child{}
 	#quiz-nav-list li{
 	width: 25%!important;text-align: center;
/*  	border-right:1px solid #ededed; */
 	} 
	#quiz-nav-list li:first-child{
/* 	border-left:1px solid #ededed; */
	}
	#quiz-nav-list li{
		font-size:1em;font-weight:900;		
	} 
	#quiz-nav-list li.selected ::after{
		content:"도전중";
	} 
/* 	#quiz-nav-list li.selected>span::before{content:"_"} */
/* 	#quiz-nav-list li.selected>span::after{content:"_"} */
	#quiz-list{overflow: hidden;}
	#quiz-list span{display:inline-block;padding: 0 2px;}    
	#quiz-list li{width:100%;}
	#quiz-list li h4{font-size:20px;}
	
	
	.quiz-content{padding:10px;}
	
	.answer-list li{padding:10px;}
	.answer-list li span{vertical-align: middle;padding:0 2px;}
	
	
	
/*progressbar*/
.progressbar {
	margin-bottom: 30px;
	overflow: hidden;
	/*CSS counters to number the steps*/
	counter-reset: step;
  	text-align: center;
   	background:#555273;
  
}
.progressbar li {
	list-style-type: none;
	color: white;
	text-transform: uppercase;
	font-size: 9px;
	width: 33.33%;
	float: left;
	position: relative;
}
.progressbar li:before {
	content: counter(step);
	counter-increment: step;
	width: 20px;
	line-height: 20px;
	display: block;
	font-size: 10px;
	color: #333;
	background: white;
	border-radius: 3px;
	margin: 0 auto 5px auto;
}
/*progressbar connectors*/
.progressbar li:after {
	content: '';
	width: 100%;
	height: 2px;
	background: white;
	position: absolute;
	left: -50%;
	top: 9px;
	z-index: -1; /*put it behind the numbers*/
}
.progressbar li:first-child:after {
	/*connector not needed before the first step*/
	content: none; 
}
/*marking active/completed steps green*/
/*The number of the step and the connector before it = green*/
.progressbar li.active:before,  .progressbar li.active:after{
	background: #27AE60;
	color: white;
}
	
	
</style>
<script type="text/javascript" src="<c:url value="/resources/js/learning/quiz.js"/>"></script>
</head>
<body>
	<%@include file="/WEB-INF/views/include/header.jsp"%>
	<main data-num="${unit.unitNum}" data-next="${nextUnitNum}">
	<div class="quiz_container">
		<div>
			<h2 id="unit-title"><span class="unit-depth">${unit.chapterDepth}</span>${unit.title} 의 마무리문제</h2>
		</div>
		<!-- 퀴즈가 표시됨 -->
<%-- 		${quizMetas} --%>
		<nav id="quiz-nav">
			<ul id="quiz-nav-list" class="progressbar">
				<c:forEach items="${quizMetas}" var="meta" varStatus="status">						
					<li id="quiz-nav-${meta.quizNum}" class="quiz-nav ${(meta.passesQuiz eq 'T') ? 'passed':' ' }  ${status.count eq 1 ? 'selected' : ''  }" data-num="${meta.quizNum}">
						<span class="msg"></span>
					</li>															
				</c:forEach>
			</ul>
		</nav>
		<div>
			<ul id="quiz-list">
				<c:forEach var="quiz" items="${quizList }" varStatus="status">
				<!-- 문제 제목-->
				<li id="quiz-item-${quiz.quizNum}" class="quiz-item ${status.count eq 1 ? 'selected' : ''  }" data-num="${quiz.quizNum}">
				<form id="quiz-${quiz.quizNum}">
				<h4 class="quiz-title">${quiz.title }</h4>		
				<div class="quiz-content">${quiz.content }</div>
				<ul class="answer-list">
				<!-- 구분선 : 답안 내용-->
					<c:forEach var="answer" items="${quiz.answerList }" varStatus="status">
						<li id="answer-${answer.answerNum}"><span>${status.count}</span><label>${answer.content} <input type="checkbox" name="quiz-answers" value="${answer.answerNum}"> </label></li>
					</c:forEach>
				</ul>
				
				<!-- 풀이가 표시됨	-->				
				<div class="solution"></div>
				<button type="button" data-num="${quiz.quizNum}" class="btn-submit-answer">정답확인</button>
				</form>
				<!-- 구분선 -->
				<c:if test="${!status.last}">
					<hr>
				</c:if>
				</li>
				</c:forEach>
			</ul>
		</div>
	</div>
	</main>
	<%@include file="/WEB-INF/views/include/footer.jsp"%>
	<script>
	</script>
	
</body>
</html>
