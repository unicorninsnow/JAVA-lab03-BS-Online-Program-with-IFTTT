package taskServlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import background.MsgTableManager;
import background.ResultSet2Json;
import background.TaskTableManager;

/**
 * 根据任务ID得到某一个任务信息
 * @author mzs
 *created on 2013-12-19
 */
public class GetTaskDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetTaskDetails() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)  {
		// TODO Auto-generated method stub
		TaskTableManager opObj = new TaskTableManager();
		String taskID = request.getParameter("taskID");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			String result = ResultSet2Json.resultSetToJson(opObj.getTaskDetails(taskID));
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
		TaskTableManager opObj = new TaskTableManager();
		String taskID = request.getParameter("taskID");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			String result = ResultSet2Json.resultSetToJson(opObj.getTaskDetails(taskID));
			out.print(result);
		} catch (Exception e) {
		    out.print("error");
		}
	}

}
