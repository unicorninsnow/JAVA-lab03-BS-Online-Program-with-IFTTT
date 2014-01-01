package taskServlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import background.ResultSet2Json;
import background.TaskTableManager;

/**
 * 新建任务
 * @author mzs
 *created on 2013-12-19
 */
public class CreateNewTask extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateNewTask() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		System.out.print("hehec");
		PrintWriter out = null;
		
		try {
			out = response.getWriter();
		String taskName = request.getParameter("taskName");
		String taskBuilder = request.getParameter("taskBuilder");
		String  taskDeadTime = request.getParameter("taskDeadTime");
		System.out.print("hehec");
		int taskTHISType = Integer.parseInt(request.getParameter("taskTHISType"));
		int taskTHATType = Integer.parseInt(request.getParameter("taskTHATType"));
		System.out.print("hehecs");
		String srcMailBox = request.getParameter("srcMailBox");
		String srcMailPassWd = request.getParameter("srcMailPassWd");
		String updateWeiboID = request.getParameter("updateWeiboID");
		String updateWeiboPassWd = request.getParameter("updateWeiboPassWd");
		String dstMailBox = request.getParameter("dstMailBox");
		String  listenWeiboID = request.getParameter("listenWeiboID");
		String listenWeiboPassWd = request.getParameter("listenWeiboPassWd");
		String content = request.getParameter("content");
		String mailSubject = request.getParameter("mailSubject");
		String weiboCheckCon = request.getParameter("weiboCheckCon");
		TaskTableManager opObj = new TaskTableManager();
		
		opObj.createNewTask(taskName, taskBuilder, taskDeadTime, taskTHISType, taskTHATType, srcMailBox, srcMailPassWd, updateWeiboID, updateWeiboPassWd, dstMailBox, listenWeiboID, listenWeiboPassWd, content, mailSubject, weiboCheckCon);
		out.print("true");
		} catch (NumberFormatException e1) {
		    out.print("error");
		} 
		catch (Exception e) {
		    out.print("error");
		}
	}

	


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.print("hehec");
		PrintWriter out = null;
		try {
			out = response.getWriter();
		String taskName = request.getParameter("taskName");
		String taskBuilder = request.getParameter("taskBuilder");
		String  taskDeadTime = request.getParameter("taskDeadTime");
		int taskTHISType = Integer.parseInt(request.getParameter("taskTHISType"));
		int taskTHATType = Integer.parseInt(request.getParameter("taskTHATType"));
		String srcMailBox = request.getParameter("srcMailBox");
		String srcMailPassWd = request.getParameter("srcMailPassWd");
		String updateWeiboID = request.getParameter("updateWeiboID");
		String updateWeiboPassWd = request.getParameter("updateWeiboPassWd");
		String dstMailBox = request.getParameter("dstMailBox");
		String  listenWeiboID = request.getParameter("listenWeiboID");
		String listenWeiboPassWd = request.getParameter("listenWeiboPassWd");
		String content = request.getParameter("content");
		String mailSubject = request.getParameter("mailSubject");
		String weiboCheckCon = request.getParameter("weiboCheckCon");
		TaskTableManager opObj = new TaskTableManager();
		opObj.createNewTask(taskName, taskBuilder, taskDeadTime, taskTHISType, taskTHATType, srcMailBox, srcMailPassWd, updateWeiboID, updateWeiboPassWd, dstMailBox, listenWeiboID, listenWeiboPassWd, content, mailSubject, weiboCheckCon);
		out.print("true");
		} catch (NumberFormatException e1) {
		    out.print("error");
		} 
		catch (Exception e) {
		    out.print("error");
		}
	}

}
