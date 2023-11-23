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

@WebServlet("/article/modify")

public class ArticleModifyServlet extends HttpServlet {
	
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		Connection conn = null; // 접속정보 알기위한 변수
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://127.0.0.1:3306/jsp_article_manager?"; //DB주소
			
			conn = DriverManager.getConnection(url, "root", "");  // root는 아이디 pw 는 없으므로 공백
			
			int id = Integer.parseInt(request.getParameter("id"));
			
			SecSql sql = new SecSql();
			
			sql.append("SELECT *");
			sql.append("FROM article");
			sql.append("WHERE id = ?", id);
			
			Map<String, Object> articleMap = DBUtil.selectRow(conn, sql);
			
			HttpSession session = request.getSession();
			
			if(session.getAttribute("loginedMemberId") == null) {
				response.setContentType("text/html; charset=UTF-8");
				response.getWriter().append(String.format("<script> alert('로그인 후 이용해주세요.'); location.replace('../member/login');</script>"));
				return;
			}
			
			if(session.getAttribute("loginedMemberId") != articleMap.get("memberId")) {
				response.setContentType("text/html; charset=UTF-8");
				response.getWriter().append(String.format("<script> alert('사용권한이 없습니다.'); location.replace('../article/detail?id=%d');</script>", id));
				return;
			}
			
			request.setAttribute("articleMap", articleMap); // 세팅할거야~ articleListMap이라는 이름의 키한테 articleListMap 값 저장
			
			request.getRequestDispatcher("/jsp/article/modify.jsp").forward(request, response);
			
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
