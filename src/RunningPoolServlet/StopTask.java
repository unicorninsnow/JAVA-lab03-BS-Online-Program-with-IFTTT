package RunningPoolServlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import background.RunningTaskPool;

/**
 * 停止任务
 * @author mzs
 *created on 2013-12-20
 */
public class StopTask extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StopTask() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)  {
		// TODO Auto-generated method stub
		String name = request.getParameter("name");
		String taskID = request.getParameter("taskID");
		RunningTaskPool opObj = new RunningTaskPool();
		PrintWriter out = null;
		try {
			out = response.getWriter();
			opObj.stopTask(name, taskID);
			out.print("true");
		} catch (Exception e) {
		    out.print("error");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)  {
		// TODO Auto-generated method stub
		String name = request.getParameter("name");
		String taskID = request.getParameter("taskID");
		RunningTaskPool opObj = new RunningTaskPool();
		PrintWriter out = null;
		try {
			out = response.getWriter();
			opObj.stopTask(name, taskID);
			out.print("true");
		} catch (Exception e) {
		    out.print("error");
		}
	}

}
