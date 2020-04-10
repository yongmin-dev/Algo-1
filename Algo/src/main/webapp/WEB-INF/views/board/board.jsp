<!-- 게시판 화면 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<%@include file="/WEB-INF/views/include/meta.jsp"%>
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/css/board.css"/>" />
 <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.8.2/css/all.min.css" />
<script type="text/javascript"
	src="<c:url value="/resources/js/board.js"/>"></script>
	
	<style>
	.container{background:#fff;height:auto;}
	.paging{height:100px;clear:both;}
	.footer{width:100%;}
	.btn_write{display:inline-block;padding:5px 10px;float:right;background:#ededed;}
	</style>
	
	
</head>

<script>

</script>


<body>
	<%@include file="/WEB-INF/views/include/header.jsp"%>
	
		<!-- 이곳에 페이지의 컨텐츠가 담김 -->


<div class="container clear">
<div class="board_wrap clear">
      <form action="">
    <div class="board_header">
      <h3>질문게시판</h3>
      <div class="search_wrap" id="search">
                  <select name="searchType" id="searchType">
               <option value="T"
                  <c:out value="${pm.rc.searchType == 'T' ? 'selected':'' }" />>제목</option>
               <option value="C"
                  <c:out value="${pm.rc.searchType == 'C' ? 'selected':'' }" />>내용</option>
               <option value="N"
                  <c:out value="${pm.rc.searchType == 'N' ? 'selected':'' }" />>작성자</option>               
            </select> 
        <input type="search" name="keyword" id="keyword"
               value="${pm.rc.keyword }" placeholder="search" >
           <button id="searchBtn">검색</button>
      </div>
    </div>
    <!-- 게시판 목록-->
    <div class="board_list">
    <ul>
      <li>No</li>
      <li>제목</li>
      <li>작성자</li>
      <li>작성일</li>
      <li>조회수</li>
    </ul>
    
    <c:forEach var="record" items="${list }">
					<fmt:formatDate value="${record.createdAt}"
						pattern="yyyy/MM/dd" var="cdate" />
					<ul>
						<li>${record.postNum }</li>					
						<li><c:forEach begin="1" end="${record.indent }">&nbsp;&nbsp;</c:forEach>
							<c:if test="${record.indent > 0 }"></c:if>
							 <a href="${pageContext.request.contextPath }/board/post/${record.postNum }/${pm.rc.reqPage}">
								${record.title }</a></li>
						<li>${record.userName }(${record.userNum })</li>
						<li>${cdate}</li>
						<li>${record.hit }</li>
					</ul>
				</c:forEach>
    
    
    
    
    
  </div>
  <div class="btn_write clear">
  	<a href="${pageContext.request.contextPath}/board/posting/${(empty pageNum)? '1': pageNum}">글쓰기</a>    
  </div>

   <div class="paging">
         <i class="fas fa-chevron-left"></i>
         <div id="paging">
            <c:set var="search" value="/${pm.rc.searchType}/${pm.rc.keyword}"></c:set>
            
            <c:if test="${empty pm.rc.searchType or empty pm.rc.keyword }">
               <c:set var="search" value=""></c:set>
            </c:if>
            <!-- 처음페이지 / 이전페이지 이동 -->
            <c:if test="${pm.prev }">
               <a href="${pageContext.request.contextPath }/board/list/1/">처음</a>
               <a
                  href="${pageContext.request.contextPath }/board/list/${pm.startPage-1}${search}">이전</a>
            </c:if>
            <c:forEach var="pageNum" begin="${pm.startPage }"
               end="${pm.endPage }">
               <!-- 현재페이지와 요청페이지가 다르면 -->
               <c:if test="${pm.rc.reqPage != pageNum }">
                  <a   href="${pageContext.request.contextPath }/board/list/${pageNum }${search}"
                     class="off">${pageNum }</a>
               </c:if>
               <!-- 현재페이지와 요청페이지가 같으면 -->
               <c:if test="${pm.rc.reqPage == pageNum }">
                  <a   href="${pageContext.request.contextPath }/board/list/${pageNum }${search}"
                     class="on">${pageNum }</a>
               </c:if>
            </c:forEach>
            <!-- 다음페이지 / 최종페이지 이동 -->
            <c:if test="${pm.next }">
               <a   href="${contextRoot }/board/list/${pm.endPage+1}/${pm.rc.searchType}/${pm.rc.keyword}">다음</a>
               <a
                  href="${contextRoot }/board/list/${pm.finalEndPage}/${pm.rc.searchType}/${pm.rc.keyword}">최종</a>
                  
            </c:if>         </div>
              <i class="fas fa-chevron-right"></i>
      </div>
  </form>
  
  

  
<%-- </div>     
		<div>
			<table>
				<tr>
					<th>번호</th>
					<th>제목</th>
					<th>작성자</th>
					<th>작성일</th>
					<th>조회수</th>
				</tr>
				<c:forEach var="record" items="${list }">
					<fmt:formatDate value="${record.createdAt }"
						pattern="yyyy/MM/dd" var="cdate" />
					<tr>
						<td>${record.postNum }</td>
						<td><c:forEach begin="1" end="${record.indent }">&nbsp;&nbsp;</c:forEach>
							<c:if test="${record.indent > 0 }"></c:if>
							 <a href="${pageContext.request.contextPath }/board/post/${record.postNum }/${pm.rc.reqPage}">
								${record.title }</a>
						<td>${record.userName }(${record.userNum })</td>
						<td>${record.createdAt }</td>
						<td>${record.hit }</td>
					</tr>
				</c:forEach>
			</table>
		</div> --%>
</div>
</div>

	
	<%@include file="/WEB-INF/views/include/footer.jsp"%>
</body>
</html>
