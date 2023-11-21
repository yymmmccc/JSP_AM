package com.koreaIT.java.am;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

import com.koreaIT.java.am.util.DBUtil;
import com.koreaIT.java.am.util.SecSql;

@WebServlet("/member/doJoin")
public class MemberDoJoinServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8"); // 아래 코드를 텍스트html로 생각하겠다.
		
		Connection conn = null; // 접속정보 알기위한 변수
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://127.0.0.1:3306/jsp_article_manager?"; //DB주소
			
			conn = DriverManager.getConnection(url, "root", "");  // root는 아이디 pw 는 없으므로 공백
			
			String loginId = request.getParameter("loginId");
			String loginPw = request.getParameter("loginPw");
			//String loginPwChk = request.getParameter("loginPwChk");
			String name = request.getParameter("name");
			
			SecSql sql = new SecSql();
			
			sql.append("SELECT COUNT(*) FROM member");
			sql.append("WHERE loginId = ?", loginId);
			
			int idChk = DBUtil.selectRowIntValue(conn, sql);
			if(idChk != 0) System.out.println("이미 사용중인 아이디");
			
			sql = new SecSql();
			
			sql.append("INSERT INTO member");
			sql.append("SET regDate = NOW(), ");
			sql.append("updateDate = NOW(), ");
			sql.append("loginId = ?, ", loginId);
			sql.append("loginPw = ?, ", loginPw);
			sql.append("name = ?", name);
			
			DBUtil.insert(conn, sql);
			
			response.getWriter().append(String.format("<script>alert('%s 님 환영합니다.'); location.replace('../home/main')</script>", name));
			
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
