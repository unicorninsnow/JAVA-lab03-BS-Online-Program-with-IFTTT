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
 * 用户取得发给自己的站内信
 * @author mzs
 *created  on 2013-12-19
 */
public class GetInstationMessage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetInstationMessage() {
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
			String result = ResultSet2Json.resultSetToJson(opObj.getInstationMessage(name));
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
			String result = ResultSet2Json.resultSetToJson(opObj.getInstationMessage(name));
			out.print(result);
		} catch (Exception e) {
		    out.print("error");
		}
	}

}
