<!-- 모든페이지에서 사용할 헤더를 정의함 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="header clear">
  <div class="header_inner clear">
  <h1 class="logo">
    <a href="#"><img src="<c:url value="/resources/images/HatchfulExport-All (2)/logo.png"/>" alt=""></a>
  </h1>

<!-- 비로그인 -->
<div class="upper_menu">
    <ul>
      <li><a href="#">로그인</a></li>
      <li><a href="#">회원가입</a></li>
      <li><a href="#">ID/비밀번호 찾기</a></li>     
    </ul>
<!-- 로그인 -->
    <!-- <ul>
      <li><a href="#">로그인</a></li>
      <li><a href="#">회원가입</a></li>
      <li><a href="#">ID/비밀번호 찾기</a></li>      
      <li><a href="#">마이페이지</a></li>      
    </ul> -->
  </div>
  <div class="bottom_menu clear">
    <div class="nav">
      <ul>
        <li><a href="#">ALGO소개</a></li>
        <li><a href="#">이론학습</a></li>
        <li><a href="#">마무리문제</a></li>
        <li><a href="#">도전과제</a></li>
        <li><a href="#">컨텐츠</a></li>
        <li><a href="#">랭킹보기</a></li>
        <li><a href="#">게시판</a></li>
    </ul>
    </div>
  </div>
</div>
<div class="sub_menu">
  <div class="sub_menu_inner">
    <ul>
      <li><a href="">ALGO소개</a></li>
      <li><a href="">· 소개</a></li>
      <li><a href="">· 연혁</a></li>      
    </ul>
    <ul>
      <li><a href="">이론학습</a></li>
      <li><a href="">· 이론학습목록</a></li>
      <li><a href=""></a></li>
      <li><a href=""></a></li>
    </ul>
    <ul>
      <li><a href="">마무리 문제 목록</a></li>
      <li><a href="">· 마무리 문제 풀기
      </a></li>
      <li><a href="">· 마무리 문제 풀이보기
      </a></li>
      <li><a href=""></a></li>
    </ul>
    <ul>
      <li><a href="">도전과제</a></li>
      <li><a href="">· 도전과제조회</a></li>
      <li><a href="">· 내가 푼 문제</a></li>
      <li><a href="">· 도전과제 화면</a></li>
      <li><a href="">· 도전과제 결과</a></li>
      <li><a href="">· 도전과제 풀기화면</a></li>
      
    </ul>
    <ul>
      <li><a href="">컨텐츠</a></li>
      <li><a href="">· 이론학습 관리</a></li>
      <li><a href="">· 도전과제 관리</a></li>
      <li><a href="">· 튜터컨텐츠리스트</a></li>
    </ul>
    <ul>
      <li><a href="">랭킹보기</a></li>
      <li><a href="">· 랭킹조회</a></li>
      <li><a href=""></a></li>
      <li><a href=""></a></li>
    </ul>
    <ul>
      <li><a href="">게시판</a></li>
      <li><a href="">· 질문게시판</a></li>
      <li><a href=""></a></li>
      <li><a href=""></a></li>
    </ul>
</div>
</div>
</div>


<!-- header end -->