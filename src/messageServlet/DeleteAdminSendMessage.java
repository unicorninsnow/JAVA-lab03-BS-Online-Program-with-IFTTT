package messageServlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import background.MsgTableManager;

@WebServlet("/DeleteAdminSendMessage")
/**
 * 删除管理员发布的消息，只有管理员有此权限
 * @author mzs
 *created on 2013-12-19
 */
public class DeleteAdminSendMessage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteAdminSendMessage() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String messageID = request.getParameter("messageID");
		MsgTableManager opObj = new MsgTableManager();
		PrintWriter out = response.getWriter();
		try {
			int privilege = Integer.parseInt(request.getParameter("privilege"));
			boolean flag = opObj.deleteAdminSendMessage(privilege, messageID);
			out.print(flag);
		} catch (Exception e) {
		    out.print("error");
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String messageID = request.getParameter("messageID");
		MsgTableManager opObj = new MsgTableManager();
		PrintWriter out = response.getWriter();
		try {
			int privilege = Integer.parseInt(request.getParameter("privilege"));
			boolean flag = opObj.deleteAdminSendMessage(privilege, messageID);
			out.print(flag);
		} catch (Exception e) {
		    out.print("error");
		}
	}

}
