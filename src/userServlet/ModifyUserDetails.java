package userServlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import background.UserTableManager;


@WebServlet("/ModifyUserDetails")
/**
 * 修改会员资料
 * @author mzs
 *created on 2013-12-19
 */
public class ModifyUserDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModifyUserDetails() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String name = request.getParameter("name");
		String passWd = request.getParameter("passWd");
		String birthDate = request.getParameter("birthDate");
		String imageUrl = request.getParameter("imageUrl");
		String email = request.getParameter("email");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			int sex = Integer.parseInt(request.getParameter("sex"));
			UserTableManager modifyUser = new UserTableManager();
			boolean flag = modifyUser.modifyUserData(name, passWd, sex, birthDate, imageUrl, email);
			out.print(flag);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			out.print("error");
		} 
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String name = request.getParameter("name");
		String passWd = request.getParameter("passWd");
		String birthDate = request.getParameter("birthDate");
		String imageUrl = request.getParameter("imageUrl");
		String email = request.getParameter("email");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			int sex = Integer.parseInt(request.getParameter("sex"));
			UserTableManager modifyUser = new UserTableManager();
			boolean flag = modifyUser.modifyUserData(name, passWd, sex, birthDate, imageUrl, email);
			out.print(flag);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			out.print("error");
		} 
	}

}
