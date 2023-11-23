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

@WebServlet("/article/doWrite")
public class ArticleDoWriteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html; charset=UTF-8"); // 아래 코드를 텍스트html로 생각하겠다.
		
		Connection conn = null; // 접속정보 알기위한 변수
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://127.0.0.1:3306/jsp_article_manager?"; //DB주소
			
			conn = DriverManager.getConnection(url, "root", "");  // root는 아이디 pw 는 없으므로 공백
			
			String title = request.getParameter("title");
			String body = request.getParameter("body");
			
			HttpSession session = request.getSession();
			int loginedMemberId = (int)session.getAttribute("loginedMemberId");
			
			SecSql sql = new SecSql();
			
			sql.append("INSERT INTO article");
			sql.append("SET regDate = NOW(), ");
			sql.append("updateDate = NOW(), ");
			sql.append("title = ?, ", title);
			sql.append("body = ?, ", body);
			sql.append("memberId = ?", loginedMemberId);
			
			DBUtil.insert(conn, sql);
			
			response.getWriter().append("<script>alert('게시물 생성 성공!'); location.replace('list')</script>");
			
			//request.getRequestDispatcher("../article/list").forward(request, response);
			
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
