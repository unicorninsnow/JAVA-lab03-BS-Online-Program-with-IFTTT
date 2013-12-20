package userServlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import background.ResultSet2Json;
import background.UserTableManager;



/**
 * 查看所有会员资料<br>
 * 只有管理员有此权限，所以参数有一个privilege，可以从session得到
 * @author mzs
 *
 */
public class lookupUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public lookupUser() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		int privilege = Integer.parseInt(request.getParameter("privilege"));
		PrintWriter out = null;
		try {
			out = response.getWriter();
			UserTableManager  lookupUser = new UserTableManager();
			String userData = ResultSet2Json.resultSetToJson(lookupUser.lookupUser(privilege));
			out.print(userData);
			} catch (Exception e) {
				//出现错误
				  out.print("error");
			}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response){
		// 权限，开始时候就把权限存在session里头
		int privilege = Integer.parseInt(request.getParameter("privilege"));
		PrintWriter out = null;
		try {
			out = response.getWriter();
			UserTableManager  lookupUser = new UserTableManager();
			String userData = ResultSet2Json.resultSetToJson(lookupUser.lookupUser(privilege));
			out.print(userData);
			} catch (Exception e) {
				//出现错误
				  out.print("error");
			}
	}

}
