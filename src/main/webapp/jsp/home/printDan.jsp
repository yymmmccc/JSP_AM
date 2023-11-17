<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 
 <%
	String inputDan = request.getParameter("dan");
 	String inputLimit = request.getParameter("limit");
 	String inputColor = request.getParameter("color");
 	
 	if(inputDan == null){
 		inputDan = "1";
 	}
 	
 	if(inputLimit == null){
 		inputLimit = "1";
 	}
 	
 	if(inputColor == null){
 		inputColor = "black";
 	}
 	
 	int dan = Integer.parseInt(inputDan);
 	int limit = Integer.parseInt(inputLimit);
 	
	// int inputDan = 7;
	// jsp 파일에서 자바를 사용할 수 있게해줌  %= 는 변수를 출력할 때 사용
 %> 
 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>구구단</title>
</head>
<body>
	<div style="color:<%= inputColor %>;">== <%= dan %>단 ==</div>
	<%
	for(int i = 1; i <= limit; ++i){
	%>
			<div  style="color:<%= inputColor %>;"><%= dan %> * <%= i %> = <%= dan * i %></div>
	<%
		}
	%>
</body>
</html>