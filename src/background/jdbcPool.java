package background;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * tomcat连接池资源类
 * @author 孟占帅<br>
 *created on 2013-12-7
 */
public final class jdbcPool {
	private static  Context ctx = null;
	private static  DataSource ds = null;
	public static DataSource getDataSource() throws NamingException{
		ctx = new InitialContext();
		ds = ((DataSource)ctx.lookup("java:comp/env/jdbc/ifttt"));
		return ds;
	}
	
}
