<%@ page import="java.util.List"%>
<%@ page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
	List<Map<String, Object>> articleListMap = (List<Map<String, Object>>) request.getAttribute("articleListMap");
	// 키 articleListMap 의 값은 오브젝트(객체)로 가져오기때문에 List<Map> 형태로 형변환 해줘야 함
	int currentPage = (int)request.getAttribute("page");
	int totalPage = (int)request.getAttribute("totalPage");
	int from = (int)request.getAttribute("from");
	int end = (int)request.getAttribute("end");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시물 리스트</title>
</head>
<body>	
	<h2>게시물 리스트</h2>
	<div><a href="../home/main">메인 페이지 이동</a></div>
	<div><a href="write">글작성</a></div>
	<table border="1">
		<colgroup>
			<col />
			<col width="200"/>
			<col />
		</colgroup>
		<tr>
			<th>번호</th>
			<th>작성일</th>
			<th>내용</th>
		</tr>
		<%
		for (Map<String, Object> articleMap : articleListMap) {
		%>
		<tr>
			<td><%=articleMap.get("id")%></td>
			<td><%=articleMap.get("regDate")%></td>
			<td><a href="../article/detail?id=<%=articleMap.get("id")%>"><%=articleMap.get("title")%></a></td>
		</tr>
		<%
		}
		%>
	</table>
	
	<style type="text/css">
		.paging > a.red{
			color: red;
			font-size: 1.2rem;
		}
	</style>
	
	<div class="paging">
		<% if(currentPage > 1){ %>
			<a href="list?page=1"> << </a> 
		<% } %>
		<% if(currentPage > 1){ %>
			<a href="list?page=<%= currentPage-1 %>"> < </a> 
		<% } %>
		<% for(int i = from; i <= end; ++i){ %>
			<a class="<%= currentPage == i ? "red" : "" %>" href="list?page=<%=i%>"><%= i %></a>
		<% } %>
		<% if(currentPage < totalPage){ %>
			<a href="list?page=<%= currentPage+1 %>"> > </a>
		<% } %>
		<% if(currentPage < totalPage){ %>
			<a href="list?page=<%= totalPage %>"> >> </a>
		<% } %>
	</div>
</body>
</html>