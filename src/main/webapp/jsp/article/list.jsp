<%@ page import="java.util.List"%>
<%@ page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
	List<Map<String, Object>> articleListMap = (List<Map<String, Object>>) request.getAttribute("articleListMap");
	// 키 articleListMap 의 값은 오브젝트(객체)로 가져오기때문에 List<Map> 형태로 형변환 해줘야 함
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시물 리스트</title>
</head>
<body>	
	<h2>게시물 리스트</h2>
	<ul>
		<% 
			for(Map<String, Object> articleMap : articleListMap){
		%>
				<li><%=articleMap.get("id")%>, <%= articleMap.get("regDate") %>, <a href="../article/detail?id=<%=articleMap.get("id")%>"><%=articleMap.get("title")%></a></li>
		<%
			}
		%>
	</ul>
	<div><a href="../home/main">메인 페이지 이동</a></div>
</body>
</html>