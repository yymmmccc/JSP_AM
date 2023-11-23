<%@ page import="java.util.Map"%>
<%@ page import="java.time.LocalDateTime"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
	Map<String, Object> articleMap = (Map<String, Object>) request.getAttribute("articleMap");
	// 키 articleListMap 의 값은 오브젝트(객체)로 가져오기때문에 List<Map> 형태로 형변환 해줘야 함
	int loginedMemberId = (int)request.getAttribute("loginedMemberId");
%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시물 상세보기</title>
</head>
<body>
	<h1><%=(int)articleMap.get("id")%>번 게시물</h1>
 	<div>글번호 : <%=(int)articleMap.get("id")%></div>
 	<div>작성일 : <%=(LocalDateTime)articleMap.get("regDate")%></div>
 	<div>작성자 : <%=(String)articleMap.get("name")%></div>
 	<div>제목 : <%=(String)articleMap.get("title")%></div>
 	<div>내용 : <%=(String)articleMap.get("body")%></div>	
 	<a href="../article/list">목록</a>
 	<% if((int)articleMap.get("memberId") == (loginedMemberId)){ %>
 	<a href="../article/doDelete?id=<%=(int)articleMap.get("id")%>" onclick="if(confirm('정말 삭제하시겠습니까?') == false) return false;">삭제</a>
 	<a href="../article/modify?id=<%=(int)articleMap.get("id")%>">수정</a>
 	<% } %>
</body>
</html>