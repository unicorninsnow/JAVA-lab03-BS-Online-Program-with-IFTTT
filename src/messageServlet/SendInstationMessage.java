package messageServlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import background.MsgTableManager;

/**
 * 发站内信
 * @author mzs
 *created on  2013-12-19
 */
public class SendInstationMessage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SendInstationMessage() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String name = request.getParameter("name");
		String receiverName = request.getParameter("receiverName");
		String messageContent = request.getParameter("messageContent");
		MsgTableManager opObj = new MsgTableManager();
		PrintWriter out = null;
		try {
			out = response.getWriter();
			boolean result = opObj.sendInstationMessage(name, receiverName, messageContent);
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
		String receiverName = request.getParameter("receiverName");
		String messageContent = request.getParameter("messageContent");
		MsgTableManager opObj = new MsgTableManager();
		PrintWriter out = null;
		try {
			out = response.getWriter();
			boolean result = opObj.sendInstationMessage(name, receiverName, messageContent);
			out.print(result);
		} catch (Exception e) {
		    out.print("error");
		}
	}

}
