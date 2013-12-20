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
 * 根据消息ID查看消息内容
 * @author mzs
 *created on 2013-12-19
 */
public class GetMessageDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetMessageDetails() {
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
			String result = ResultSet2Json.resultSetToJson(opObj.getMessageDetails(messageID));
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
		String messageID = request.getParameter("messageID");
		MsgTableManager opObj = new MsgTableManager();
		PrintWriter out = response.getWriter();
		try {
			String result = ResultSet2Json.resultSetToJson(opObj.getMessageDetails(messageID));
			out.print(result);
		} catch (Exception e) {
		    out.print("error");
		}
	}

}
