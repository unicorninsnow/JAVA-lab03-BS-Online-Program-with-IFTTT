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
 * 得到所有公告
 * @author mzs
 *created on 2013-12-19
 */
public class GetNoticeMessage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetNoticeMessage() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)  {
		MsgTableManager opObj = new MsgTableManager();
		PrintWriter out = null;
		try {
			out = response.getWriter();
			String result = ResultSet2Json.resultSetToJson(opObj.getNoticeMessage());
			out.print(result);
		} catch (Exception e) {
		    out.print("error");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)  {
		// TODO Auto-generated method stub
		MsgTableManager opObj = new MsgTableManager();
		PrintWriter out = null;
		try {
			out = response.getWriter();
			String result = ResultSet2Json.resultSetToJson(opObj.getNoticeMessage());
			out.print(result);
		} catch (Exception e) {
		    out.print("error");
		}
	}

}
