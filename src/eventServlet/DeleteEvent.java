package eventServlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import background.EventTableManager;

/**
 * 删除事件，如果有任务正在运行，则会通知用户停止掉，当然这封装在函数内了
 * @author mzs
 *created on  2014-12-20
 */
public class DeleteEvent extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteEvent() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		String  name = request.getParameter("name");
		PrintWriter out = null;
		EventTableManager opObj  = new EventTableManager();
		try {
			out = response.getWriter();
			int type = Integer.parseInt(request.getParameter("type"));
			int selectedEventType = Integer.parseInt(request.getParameter("selectedEventType"));
			boolean result = opObj.deleteEvent(name, type, selectedEventType);
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
		String  name = request.getParameter("name");
		PrintWriter out = null;
		EventTableManager opObj  = new EventTableManager();
		try {
			out = response.getWriter();
			int type = Integer.parseInt(request.getParameter("type"));
			int selectedEventType = Integer.parseInt(request.getParameter("selectedEventType"));
			boolean result = opObj.deleteEvent(name, type, selectedEventType);
			out.print(result);
		} catch (Exception e) {
		    out.print("error");
		}
	}

}
