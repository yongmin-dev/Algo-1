<!-- 게시글 보기 화면 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<%@include file="/WEB-INF/views/include/meta.jsp"%>
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/css/post-view.css"/>" />
	<script type="text/javascript"
	src="<c:url value="/resources/js/common.js"/>"></script>
	<script type="text/javascript"
	src="<c:url value="/resources/js/board/post-view.js"/>"></script>
	
	
	<style>
	#content{height:400px;border:none;padding:0;background:#fff;border: 1px solid #e2e2e2;}
	.container{background:#fff;height:auto;}
	
	button{padding:5px 15px;cursor:pointer;float:left;display:inline-block;}
	.btn_wrap{overflow:hidden;float:right;}
	#title{border-style:none; background:none; font-size: 1.5em;}
	</style>
	
</head>
<body>
	<%@include file="/WEB-INF/views/include/header.jsp"%>
<form:form
			action="${pageContext.request.contextPath }/board/modifying/${postNum }/${returnPage }"
			enctype="multipart/form-data" method="post"
			modelAttribute="boardPostVO">
			<form:hidden path="postNum" />


 <div class="container">
<div class="board_wrap">
    
    <div class="board_list_header">

      <h3><form:input id="title" type="text" path="title" readOnly="readOnly"/></h3>
    </div>
    <div class="board_summary">
      <div>
        <div class="write">${boardPostVO.userName }</div>
        <form:select path="category.categoryNum" disabled="true">
					<option value="0">=선택=</option>
					<form:options path="category.categoryNum"
						items="${boardCategoryVO }" itemValue="categoryNum"
						itemLabel="name"/>
				</form:select>
        <span>${boardPostVO.createdAt }</span>
        <span>조회수 : ${boardPostVO.hit }</span>
      </div>
    </div>
    
		<form:textarea path="content" readOnly="true" class="board_text"></form:textarea>
    
<!-- 첨부파일 -->    
	<div class="umode">
		<form:label path="files">첨부파일</form:label>
		<form:input type="file" multiple="multiple" path="files" />
	</div>
    
    <div class="btn_wrap">
    
			<!-- 버튼들 -->
			<form:button class="rmode" id="replyBtn" data-returnPage="${returnPage }"
				data-postNum="${boardPostVO.postNum }">답글달기</form:button>
			<!-- 작성자 수정 삭제 가능 -->
			<c:if test="${userVO.userNum == boardPostVO.userNum }">
				<form:button class="rmode" id="modifyBtn">수정하기</form:button>
				<form:button class="rmode" id="deleteBtn"
					data-returnPage="${returnPage }"
					data-postNum="${boardPostVO.postNum }">삭제하기</form:button>
			</c:if>
			<!-- 작성자 수정 삭제 완료 -->
			<form:button class="umode" id="cancelBtn">취소하기</form:button>
			<form:button class="umode" id="saveBtn">저장하기</form:button>
			<form:button id="listBtn" data-returnPage="${returnPage }">목록보기</form:button>
	</div>
			
      <div class="board_reply">
        <div class="board_summary">
          <div>
            <div class="write">관리자</div>
            <span>2020-02-02</span>
            <span>조회수 366</span>
          </div>
          <div class="reply_text">
			공지 숙지 하였습니다. 감사합니다.
          </div>
        </div>
      </div>
    </div>
</div>     
  
  </form:form>
	



	<!-- 댓글 -->
	<%@include file="/WEB-INF/views/board/comment.jsp"%>

	<%@include file="/WEB-INF/views/include/footer.jsp"%>
</body>
</html>
