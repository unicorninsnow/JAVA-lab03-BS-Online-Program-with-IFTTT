package userServlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import background.UserTableManager;


@WebServlet("/RegisterAccount")
/**
 * 注册账号
 * @author mzs
 *created on 2013-12-19
 */
public class RegisterAccount extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     *  Default constructor. 
     */
    public RegisterAccount() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response)  {
		// TODO Auto-generated method stub
		String name = request.getParameter("name");
		String passWd = request.getParameter("passWd");
		int sex = Integer.parseInt(request.getParameter("sex"));
		String birthDate = request.getParameter("birthDate");
		String imageUrl = request.getParameter("imageUrl");
		String email = request.getParameter("email");
		PrintWriter out = null;
		try {
			UserTableManager registerAccount = new UserTableManager();
			registerAccount.userRegister(name, passWd, sex, birthDate, imageUrl, email);
			out = response.getWriter();
			out.println("true");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			out.println("error");
		} 
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String name = request.getParameter("name");
		String passWd = request.getParameter("passWd");
		int sex = Integer.parseInt(request.getParameter("sex"));
		String birthDate = request.getParameter("birthDate");
		String imageUrl = request.getParameter("imageUrl");
		String email = request.getParameter("email");
		PrintWriter out = null;
		try {
			UserTableManager registerAccount = new UserTableManager();
			registerAccount.userRegister(name, passWd, sex, birthDate, imageUrl, email);
			out = response.getWriter();
			out.print("true");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			out.print("error");
		} 
		
	}

}
