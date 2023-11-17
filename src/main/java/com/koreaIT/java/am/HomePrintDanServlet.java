package com.koreaIT.java.am;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/home/printDan") 

// 1. /home/printDan 도메인 요청들어오면 doGet 메서드가 일을할꺼야
// 2. request에 사용자가 요청한 데이터들이 들어있고 그것을 /jsp/home/ 경로에 printDan.jsp에게 넘겨줄꺼야 

public class HomePrintDanServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/jsp/home/printDan.jsp").forward(request, response);
		// 웹에서 사용자가 요청한 데이터들이 request에 담아져있는데 /jsp/home/ 경로에 printDan.jsp파일에게 데이터들을 넘겨라
		
	}

}
