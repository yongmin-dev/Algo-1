<!-- 도전과제 관리 화면 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html data-contextpath="${pageContext.request.contextPath}">
<head>
	<meta charset="UTF-8">
	<title>도전과제 관리</title>
	<%@include file="/WEB-INF/views/include/meta.jsp" %>	
	<style type="text/css">
		#challenge-list li.selected{
			    background: #555273;
    color: #fff;
		}
		  .chg_cont{width: 980px;margin: 0 auto;}
  #challenge-list{ border:none; overflow: hidden;padding: 50px 0;}
  #challenge-list li{width: 50%;float: left;border:1px solid #e2e2e2;box-sizing: border-box;padding: 50px;text-align: center;}
  #challenge-list li span.title{font-size:20px;font-weight: bold;}
  #challenge-list li button{cursor: pointer;padding: 10px 20px;margin: 0 2px;}
  h3{display: inline-block;font-size: 20px;}
  #btn-editor,#btn-new-challenge{padding: 10px 20px;cursor: pointer;}
  main>div{padding:30px 0 0 0;}
	</style>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/content-manager.js"></script>
	
</head>
<body>
	<%@include file="/WEB-INF/views/include/header.jsp" %>
	<div class="chg_cont">
	<main>
    <div>
      <h3>도전과제 관리</h3>
      <!-- 편집기 : 누르면 선택한 도전과제의 편집기 화면으로 감-->
      <button id="btn-editor" type="button">편집기</button>
      <!-- 도전과제를 추가함 -->
      <button id="btn-new-challenge" type="button">새 도전과제</button>
    </div>
		<!-- 도전과제 리스트 -->
		<ul class="content-list" id="challenge-list">
			
		</ul>
	</main>	
	</div>
	<%@include file="/WEB-INF/views/include/footer.jsp" %>
</body>
</html>
