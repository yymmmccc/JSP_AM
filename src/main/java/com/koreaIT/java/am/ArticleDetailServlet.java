package com.koreaIT.java.am;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

import com.koreaIT.java.am.util.DBUtil;
import com.koreaIT.java.am.util.SecSql;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/article/detail")

public class ArticleDetailServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		Connection conn = null; // 접속정보 알기위한 변수
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://127.0.0.1:3306/jsp_article_manager?"; //DB주소
			
			conn = DriverManager.getConnection(url, "root", "");  // root는 아이디 pw 는 없으므로 공백
			
			int id = Integer.parseInt(request.getParameter("id"));
			
			SecSql sql = new SecSql();
			
			sql.append("SELECT article.*, member.name");
			sql.append("FROM article");
			sql.append("INNER JOIN member");
			sql.append("ON article.memberId = member.id");
			sql.append("WHERE article.id = ?", id);
			
			Map<String, Object> articleMap = DBUtil.selectRow(conn, sql);
			
			int loginedMemberId = -1;
			
			HttpSession session = request.getSession();
			
			if(session.getAttribute("loginedMemberId") != null) {
				loginedMemberId = (int)session.getAttribute("loginedMemberId");
			}
			
			request.setAttribute("loginedMemberId", loginedMemberId);
			request.setAttribute("articleMap", articleMap); // 세팅할거야~ articleListMap이라는 이름의 키한테 articleListMap 값 저장
			
			request.getRequestDispatcher("/jsp/article/detail.jsp").forward(request, response);
			
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

}
