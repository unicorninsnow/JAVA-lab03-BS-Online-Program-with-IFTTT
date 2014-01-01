package eventServlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import background.EventTableManager;
import background.ResultSet2Json;

/**
 * 查询未被添加的任务
 * @author mzs
 *created on  2013-12-20
 */
public class GetNonAddedEvent extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetNonAddedEvent() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)  {
		EventTableManager opObj = new EventTableManager();
		PrintWriter out = null;
		try {
			out = response.getWriter();
			int type = Integer.parseInt(request.getParameter("type"));
			String result = ResultSet2Json.resultSetToJson(opObj.getNonAddedEvent(type));
			out.print(result);
		} catch (Exception e) {
		    out.print("error");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {

		EventTableManager opObj = new EventTableManager();
		PrintWriter out = null;
		try {
			out = response.getWriter();
			int type = Integer.parseInt(request.getParameter("type"));
			String result = ResultSet2Json.resultSetToJson(opObj.getNonAddedEvent(type));
			out.print(result);
		} catch (Exception e) {
		    out.print("error");
		}
	}

}
