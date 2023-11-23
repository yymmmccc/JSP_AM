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

@WebServlet("/article/doDelete")
public class ArticleDoDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html; charset=UTF-8"); // 아래 코드를 텍스트html로 생각하겠다.
		
		Connection conn = null; // 접속정보 알기위한 변수
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://127.0.0.1:3306/jsp_article_manager?"; //DB주소
			
			conn = DriverManager.getConnection(url, "root", "");  // root는 아이디 pw 는 없으므로 공백
			
			int id = Integer.parseInt(request.getParameter("id"));
			
			SecSql sql = new SecSql();
			
			sql.append("SELECT * FROM");
			sql.append("article");
			sql.append("WHERE id = ?", id);
			
			Map<String, Object> articleMap = DBUtil.selectRow(conn, sql);
			if(articleMap.isEmpty()) {
				response.setContentType("text/html; charset=UTF-8");
				response.getWriter().append(String.format("<script> alert('해당글은 존재하지 않습니다.'); location.replace('../home/main');</script>"));
				return;
			}
			
			int loginedMemberId = -1;
			
			HttpSession session = request.getSession();
			
			if(session.getAttribute("loginedMemberId") != null) {
				loginedMemberId = (int)session.getAttribute("loginedMemberId");
			}
			
			if(loginedMemberId == -1) {
				response.setContentType("text/html; charset=UTF-8");
				response.getWriter().append(String.format("<script> alert('로그인 후 이용해주세요.'); location.replace('../member/login');</script>"));
				return;
			}
			
			if((int)articleMap.get("memberId") != loginedMemberId) {
				response.setContentType("text/html; charset=UTF-8");
				response.getWriter().append(String.format("<script> alert('권한이 없습니다.'); location.replace('../home/main');</script>"));
				return;
			}
			
			sql = new SecSql();
			
			sql.append("DELETE FROM");
			sql.append("article");
			sql.append("WHERE id = ?", id);
			
			DBUtil.delete(conn, sql);
			
			response.getWriter().append(String.format("<script>alert('%d번 게시글 삭제'); location.replace('list')</script>", id));
			
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
}
