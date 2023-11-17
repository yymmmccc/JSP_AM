package com.koreaIT.java.am;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/home/printDan") // -> 요청주소의 경로를 매핑했다 (도메인 주소를 바꿨다?) 

public class HomeMainServlet2 extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html; charset=UTF-8"); // 이 문서에서 제공하는 text는 html로 판단하겠다.
		
		String inputDan = request.getParameter("dan"); // dan은 키임 값을주면 키 = 값 임
		String inputLimit = request.getParameter("limit");
		String inputColor = request.getParameter("color");
		
		if(inputDan == null) {
			inputDan = "1";
		}
		
		int dan = Integer.parseInt(inputDan); // String 타입을 int형으로 바꿀 때는 이렇게 씀
		int limit = Integer.parseInt(inputLimit);
		
		
		response.getWriter().append(String.format("<div style='color:%s;'> == %d == </div>", inputColor, dan));
		
		for(int i = 1; i <= limit; ++i) {
			response.getWriter().append(String.format("%d * %d = %d\n<br>", dan, i, dan * i));
			
		}
	}

}
