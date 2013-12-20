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
 * 管理员修改公告
 * @author mzs
 *created on  2013-12-19
 */
public class ModifyAdminSendMessage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModifyAdminSendMessage() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		int privilege = Integer.parseInt(request.getParameter("privilege"));
		String messageID = request.getParameter("messageID");
		String content = request.getParameter("content");
		String receiver = request.getParameter("receiver");
		int type = Integer.parseInt(request.getParameter("type"));
		MsgTableManager opObj = new MsgTableManager();
		PrintWriter out = null;
		try {
			out = response.getWriter();
			int result = opObj.modifyAdminSendMessage(privilege, messageID, type, content, receiver);
			if(result==-1)
			   out.print("errorReceiver");
			else if(result==0)
				out.print("errorPrivilege");
			else
				out.print("true");
		} catch (Exception e) {
		    out.print("error");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response){
		// TODO Auto-generated method stub
		int privilege = Integer.parseInt(request.getParameter("privilege"));
		String messageID = request.getParameter("messageID");
		String content = request.getParameter("content");
		String receiver = request.getParameter("receiver");
		int type = Integer.parseInt(request.getParameter("type"));
		MsgTableManager opObj = new MsgTableManager();
		PrintWriter out = null;
		try {
			out = response.getWriter();
			int result = opObj.modifyAdminSendMessage(privilege, messageID, type, content, receiver);
			if(result==-1)
			   out.print("errorReceiver");
			else if(result==0)
				out.print("errorPrivilege");
			else
				out.print("true");
		} catch (Exception e) {
		    out.print("error");
		}
	}

}
