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
 * 管理员发公告
 * @author mzs
 *created on  2013-12-19
 */
public class PostNoticeMessage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PostNoticeMessage() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		
		String name = request.getParameter("name");
		String messageContent = request.getParameter("messageContent");
		MsgTableManager opObj = new MsgTableManager();
		PrintWriter out = null;
		try {
			out = response.getWriter();
			int privilege = Integer.parseInt(request.getParameter("privilege"));
			boolean result = opObj.postNoticeMessage(privilege, name, messageContent);
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
		String messageContent = request.getParameter("messageContent");
		MsgTableManager opObj = new MsgTableManager();
		PrintWriter out = null;
		try {
			out = response.getWriter();
			int privilege = Integer.parseInt(request.getParameter("privilege"));
			boolean result = opObj.postNoticeMessage(privilege, name, messageContent);
			out.print(result);
		} catch (Exception e) {
		    out.print("error");
		}
	}

}
