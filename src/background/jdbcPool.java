package background;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.mysql.jdbc.Driver;

/**
 * tomcat连接池资源类
 * @author 孟占帅<br>
 *created on 2013-12-7
 */
public final class jdbcPool {
//	private static  Context ctx = null;
//	private static  DataSource ds = null;
//	public static DataSource getDataSource() throws NamingException{
//		ctx = new InitialContext();
//		ds = ((DataSource)ctx.lookup("java:comp/env/jdbc/ifttt"));
//		return ds;
//	}
	
	//test
	public static Connection getDataSource() throws SQLException, ClassNotFoundException{
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/ifttt","root","");
		return conn;
	} 
	
}
