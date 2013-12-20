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

/**
 * 添加触发事件
 * @author mzs
 *created on 2013-12-20  10:06
 */
public class AddEvent extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddEvent() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		
		int privilege = Integer.parseInt(request.getParameter("privilege"));
		int type = Integer.parseInt(request.getParameter("type"));
		int selectedEventType = Integer.parseInt(request.getParameter("selectedEventType"));
		PrintWriter out = null;
		EventTableManager opObj  = new EventTableManager();
		try {
			out = response.getWriter();
			boolean result = opObj.addEvent(privilege, type, selectedEventType);
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
		int privilege = Integer.parseInt(request.getParameter("privilege"));
		int type = Integer.parseInt(request.getParameter("type"));
		int selectedEventType = Integer.parseInt(request.getParameter("selectedEventType"));
		PrintWriter out = null;
		EventTableManager opObj  = new EventTableManager();
		try {
			out = response.getWriter();
			boolean result = opObj.addEvent(privilege, type, selectedEventType);
			out.print(result);
		} catch (Exception e) {
		    out.print("error");
		}
	}

}
