package userServlet;

import background.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Login
 * 登录验证
 * @author mzs
 * created on 2013-12-19
 */

public class LoginAuthe extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public LoginAuthe() {
        // TODO Auto-generated constructor stub
    	super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		//用户名和密码
	
		    String name = request.getParameter("name");
		    String passWd = request.getParameter("passwD");
			UserTableManager loginCheck;
			PrintWriter out = null;
			try {
				out = response.getWriter();
				loginCheck = new UserTableManager();
			
			if(loginCheck.login(name, passWd))  //登录成功
		          out.print("true");
			else 
				//登录失败
				  out.print("false");
			} catch (Exception e) {
				//出现错误
				  out.print("error");
			}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//用户名和密码
		
	    String name = request.getParameter("name");
	    String passWd = request.getParameter("passwD");
		UserTableManager loginCheck;
		PrintWriter out = null;
		try {
			out = response.getWriter();
			loginCheck = new UserTableManager();
		
		if(loginCheck.login(name, passWd))  //登录成功
	          out.print("true");
		else 
			//登录失败
			  out.print("false");
		} catch (Exception e) {
			//出现错误
			  out.print("error");
		}
		
	}

}
