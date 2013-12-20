package taskServlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import background.MsgTableManager;
import background.TaskTableManager;

/**
 * 根据任务ID删除任务
 * @author mzs
 *created on 2013-12-19
 */
public class DeleteTask extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteTask() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String taskID = request.getParameter("taskID");
		PrintWriter out = response.getWriter();
		TaskTableManager opObj = new TaskTableManager();
		try {
			boolean flag = opObj.deleteTask(taskID);
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
		String taskID = request.getParameter("taskID");
		PrintWriter out = response.getWriter();
		TaskTableManager opObj = new TaskTableManager();
		try {
			boolean flag = opObj.deleteTask(taskID);
			out.print(flag);
		} catch (Exception e) {
		    out.print("error");
		}
		
	}

}
