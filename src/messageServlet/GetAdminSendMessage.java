package messageServlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import background.MsgTableManager;
import background.ResultSet2Json;

/**
 * 取得管理员发布的消息，包括站内信和公告
 * @author mzs
 *created on 2013-12-19
 */
public class GetAdminSendMessage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetAdminSendMessage() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String name = request.getParameter("name");
		MsgTableManager opObj = new MsgTableManager();
		PrintWriter out = response.getWriter();
		try {
			int privilege = Integer.parseInt(request.getParameter("privilege"));
			String result = ResultSet2Json.resultSetToJson(opObj.getAdminSendMessage(privilege, name));
			out.print(result);
		} catch (Exception e) {
		    out.print("error");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	
		String name = request.getParameter("name");
		MsgTableManager opObj = new MsgTableManager();
		PrintWriter out = response.getWriter();
		try {
			int privilege = Integer.parseInt(request.getParameter("privilege"));
			String result = ResultSet2Json.resultSetToJson(opObj.getAdminSendMessage(privilege, name));
			out.print(result);
		} catch (Exception e) {
		    out.print("error");
		}
	}

}
