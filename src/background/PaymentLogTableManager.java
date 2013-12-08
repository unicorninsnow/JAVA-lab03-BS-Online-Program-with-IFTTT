package background;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.NamingException;


/**
 * 消费记录表管理类<br>
 * 包含查看消费记录及新建消费记录的方法封装
 * @author mzs
 *created on 2013-12-8
 */
public class PaymentLogTableManager {
	/**
	 * 默认构造函数
	 */
	public PaymentLogTableManager(){
		/***
		 * 
		 * 
		 * 
		 * 
		 */
		
		
	}
      
	/**
	 * 查询得到消费记录
	 * 
	 * @param name
	 *            会员名
	 * @return 返回查询得到的结果集
	 * @throws SQLException
	 * @throws NamingException 
	 */
	public ResultSet getPaymentLog(String name) throws SQLException, NamingException {
		Connection conn = null;
		conn = jdbcPool.getDataSource().getConnection();
	    if(conn!=null){
		PreparedStatement pstmt =conn
				.prepareStatement("select * from paymentLog where name = ?");
		pstmt.setString(1, name);
		ResultSet paymentLogResultSet = pstmt.getResultSet();
		return paymentLogResultSet;
	    }
	    return null;
	}
	
	public void addPaymentLog(String name,int paymentTHIS,int paymentTHAT,String paymentTime,int paymentAmount){
		Connection conn = null;	
		PreparedStatement pst;
			try {
				conn = jdbcPool.getDataSource().getConnection();
			    if(conn!=null){
				pst = conn.prepareStatement("insert into paymentlog (paymentOwener,paymentTHIS,paymentTHAT,paymentTime,paymentAmount) values (?,?,?,?,?)");
			pst.setString(1, name);
			pst.setInt(2, paymentTHIS);
			pst.setInt(3, paymentTHAT);
			pst.setString(4, paymentTime);
			pst.setInt(5, paymentAmount);
			pst.executeUpdate();
			} 
			}catch (SQLException e) {
				// TODO Auto-generated catch block
				try {
					conn.rollback();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}  //失败则回滚
				e.printStackTrace();
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		   try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    }

	
}
