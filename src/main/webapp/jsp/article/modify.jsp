<%@ page import="java.util.Map"%>
<%@ page import="java.time.LocalDateTime"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
	Map<String, Object> articleMap = (Map<String, Object>) request.getAttribute("articleMap");
	// 키 articleListMap 의 값은 오브젝트(객체)로 가져오기때문에 List<Map> 형태로 형변환 해줘야 함
%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시물 수정</title>
</head>
<body>
	<h1><%=(int)articleMap.get("id")%>번 게시물 수정</h1>
 	<div>글번호 : <%=(int)articleMap.get("id")%></div>
 	<div>작성일 : <%=(LocalDateTime)articleMap.get("regDate")%></div>
 	<form action="doModify" method="GET">
 		<div>제목 : <input name="title" type="text" value="<%=(String)articleMap.get("title")%>"/></div>
 		<div>내용 : <textarea name="body" cols="30" rows="10"><%=(String)articleMap.get("body")%></textarea></div>
 		<input type="hidden" name="id" value="<%=(int)articleMap.get("id")%>">
 		<button>수정</button>
 	</form>
 	<a href="detail?id=<%=(int)articleMap.get("id")%>">취소</a>
 	<a href="../article/list">목록</a>
</body>
</html>