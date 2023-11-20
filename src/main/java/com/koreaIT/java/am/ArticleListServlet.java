package com.koreaIT.java.am;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import com.koreaIT.java.am.util.DBUtil;
import com.koreaIT.java.am.util.SecSql;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/article/list") // 도메인주소에 article/list가 요청이되면 WebServlet이 어 내꺼다 하고 밑에 코드 실행함
public class ArticleListServlet extends HttpServlet {
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Connection conn = null; // 접속정보 알기위한 변수
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://127.0.0.1:3306/jsp_article_manager?"; //DB주소
			
			conn = DriverManager.getConnection(url, "root", "");  // root는 아이디 pw 는 없으므로 공백
			
			int page = 1; // 시작 페이지이므로 1로 설정
			
			if(request.getParameter("page") != null && request.getParameter("page").length() != 0) {
				page = Integer.parseInt(request.getParameter("page"));
				// page에 값이 있을때는 int형으로 형변환 : 왜냐 파라미터로 받는 page는 String이므로
			}
			
			int itemsPage = 10;   // 한페이지당 게시글 갯수 10개
			
			int limitFrom = (page - 1) * itemsPage;  // 1페이지면 -> 0 , 2페이지면 -> 10
			
			SecSql sql = new SecSql();
			sql.append("SELECT COUNT(*) FROM article");
			
			int totalCount = DBUtil.selectRowIntValue(conn, sql); // 레코드 총 갯수 -> 토탈페이지를 구하기 위한 변수
			int totalPage = (int) Math.ceil((double)totalCount / itemsPage); // 레코드 총 갯수 / 10
			
			sql = new SecSql();
			sql.append("SELECT *");
			sql.append("FROM article");
			sql.append("ORDER BY id DESC");
			sql.append("LIMIT ?, ?", limitFrom, itemsPage); 
			// 1페이지면 레코드 0부터 시작해서 10개보여줘 0, 1, 2... 9
			// 2페이지면 레코드 10부터 시작해서 10개 보여줘
			// 3페이지면 레코드 20부터 시작해서 10개보여줘
			
			List<Map<String, Object>> articleListMap = DBUtil.selectRows(conn, sql);
			
			request.setAttribute("page", page); // 현재 페이지 데이터 전송
			request.setAttribute("totalPage", totalPage); // 총 페이지 갯수 전송
			request.setAttribute("articleListMap", articleListMap); // 세팅할거야~ articleListMap이라는 이름의 키한테 articleListMap 값 저장
			
			request.getRequestDispatcher("/jsp/article/list.jsp").forward(request, response);
			
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
