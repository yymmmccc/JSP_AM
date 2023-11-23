package com.koreaIT.java.am;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/home/main")

public class HomeMainServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int loginedMemberId = -1;
		
		HttpSession session = request.getSession();
		
		if(session.getAttribute("loginedMemberId") != null) {
			loginedMemberId = (int)session.getAttribute("loginedMemberId"); // 키에 대한 값은 객체로 반환되기 때문에 int형으로 형변환 해줘야 함
			// 다른 서블릿 세션에서 loginedMemberId에 값을 넣었다면 Null이 아님 
			// null이 아니므로 세션에 들어있는 loginedMemberId 키의 값을 넣어줌
		}
		
		request.setAttribute("loginedMemberId", loginedMemberId);
		
		request.getRequestDispatcher("/jsp/home/main.jsp").forward(request, response);
	}
}
