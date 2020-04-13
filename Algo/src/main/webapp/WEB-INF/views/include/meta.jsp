<!-- 여기서는 모든 페이지의 head 태그에 공통적으로 들어가는 태그들을 정의함 -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/common/css/default.css"/>" />
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/common/css/slick.css"/>" />
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/common/css/slick-theme.css"/>" />
<%-- <link rel="stylesheet" type="text/css" href="<c:url value="/resources/codemirror/doc/docs.css"/>" /> --%>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/codemirror/lib/codemirror.css"/>" />
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/codemirror/addon/hint/show-hint.css"/>" />

<script type="text/javascript" src="<c:url value="/resources/common/js/jquery.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/common/js/slick.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/common/js/common.js"/>"></script>
<script src="https://cdn.ckeditor.com/ckeditor5/18.0.0/classic/ckeditor.js"></script>
<script type="text/javascript" src="<c:url value="/resources/codemirror/lib/codemirror.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/codemirror/addon/edit/matchbrackets.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/codemirror/addon/hint/show-hint.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/codemirror/mode/javascript/javascript.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/codemirror/clike.js"/>"></script>
