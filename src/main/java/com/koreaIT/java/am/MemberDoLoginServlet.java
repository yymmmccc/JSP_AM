package com.koreaIT.java.am;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.koreaIT.java.am.util.DBUtil;
import com.koreaIT.java.am.util.SecSql;

@WebServlet("/member/doLogin")

public class MemberDoLoginServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8"); // 아래 코드를 텍스트html로 생각하겠다.
		Connection conn = null; // 접속정보 알기위한 변수
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://127.0.0.1:3306/jsp_article_manager?"; //DB주소
			
			conn = DriverManager.getConnection(url, "root", "");  // root는 아이디 pw 는 없으므로 공백
			
			String loginId = request.getParameter("loginId");
			String loginPw = request.getParameter("loginPw");
			
			SecSql sql = new SecSql();
			sql.append("SELECT *");
			sql.append("FROM member");
			sql.append("WHERE loginId = ?", loginId);
			
			Map<String, Object> articleMap = DBUtil.selectRow(conn, sql);
			
			if(articleMap.isEmpty()) {
				response.getWriter().append(String.format("<script>alert('입력하신 아이디가 없습니다.'); location.replace('../member/login')</script>"));
				return; // 빈값이면 더 볼것도 없는데 밑으로 내려가니까 return으로 이 메서드 그냥 실행종료
			}
		
			if(!articleMap.get("loginPw").equals(loginPw)) {
				response.getWriter().append(String.format("<script>alert('비밀번호를 확인해주세요.'); location.replace('../member/login')</script>"));
				return;
			}
			
			HttpSession session = request.getSession(); // request.getSession()은 서버에 생성된 세션이 있으면 반환하고 없으면 새로 생성함
			session.setAttribute("loginedMemberId", articleMap.get("id"));
			
			response.getWriter().append(String.format("<script>alert('%s님 환영합니다!'); location.replace('../home/main')</script>", articleMap.get("name")));
			
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패");
		} catch (SQLException e) {
			System.out.println("에러: " + e);
		} finally {
			try {
				if (conn != null && conn.isClosed()) {
					conn.close();
				}
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
