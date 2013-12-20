package eventServlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import background.EventTableManager;
import background.MsgTableManager;
import background.ResultSet2Json;

/**
 * 返回已被添加的事件，也就是现阶段可以用来创建任务的事件
 * @author mzs
 *created on 2013-12-20
 */
public class GetAddedEvent extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetAddedEvent() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)  {
		// TODO Auto-generated method stub
		int type = Integer.parseInt(request.getParameter("type"));
		EventTableManager opObj = new EventTableManager();
		PrintWriter out = null;
		try {
			out = response.getWriter();
			String result = ResultSet2Json.resultSetToJson(opObj.getAddedEvent(type));
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
		int type = Integer.parseInt(request.getParameter("type"));
		EventTableManager opObj = new EventTableManager();
		PrintWriter out = null;
		try {
			out = response.getWriter();
			String result = ResultSet2Json.resultSetToJson(opObj.getAddedEvent(type));
			out.print(result);
		} catch (Exception e) {
		    out.print("error");
		}
	
	}

}
