package background;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import javax.naming.*;
import javax.sql.*;

/**
 * 用来管理数据库中User表及封装一些相关操作<br>
 * @author 孟占帅
 *created on 2013-12-7
 *
 */
public class UserTableManager {
	
	/**
	 * 无参默认构造函数
	 */
    public UserTableManager() throws NamingException{
		/**************************************
		 * 
		 */
	}
    

	/**
	 * 根据会员名查询会员信息
	 * 
	 * @param name
	 *            会员名称
	 * @return 查询得到的结果集
	 * @throws SQLException
	 * @throws NamingException 
	 * */
	public static ResultSet getUserDetails(String name) throws SQLException, NamingException  {
		 Connection conn = null;
		 conn = ((DataSource)jdbcPool.getDataSource()).getConnection();
		 if(conn!=null){
			 PreparedStatement pstmt = conn.prepareStatement("select * from user where name = ?");
		     pstmt.setString(1, name);
		     ResultSet memberDetailsResultSet = pstmt.getResultSet();
		     conn.close();
		     return memberDetailsResultSet;
		 }
		 else 
			 return null;
	}
	
	/**
	 * 加锁，以防数据库读写出现不一致
	 * 修改资料，会员名，权限，级别，金钱，注册时间不可修改
	 * @param name
	 *            会员名称
	 * @param passWd
	 *            密码
	 * @param sex
	 *            性别,0为男性，1为女性
	 * @param birthYear
	 *            出生年数,年龄根据出生日期得到
	 * @param imageUrl
	 *            头像
	 * @throws SQLException
	 * @throws NamingException 
	 */
	public synchronized void modifyUserData(String name, String passWd, int sex, int birthYear,
			int birthMonth, int birthDay, String imageUrl) throws SQLException, NamingException {
		Connection conn = null;
		conn = (jdbcPool.getDataSource()).getConnection();
		if(conn!=null){
			Statement modifyMemberStmt = conn.createStatement(
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet modifyMemberResultSet = modifyMemberStmt
				.executeQuery("select * from user where name = " + name + "");
			modifyMemberResultSet.next();
			modifyMemberResultSet.updateString("passWd", passWd);
			modifyMemberResultSet.updateInt("sex", sex);
			modifyMemberResultSet.updateInt("birthYear", birthYear);
			modifyMemberResultSet.updateInt("birthMonth", birthMonth);
			modifyMemberResultSet.updateInt("birthDay", birthDay);
			modifyMemberResultSet.updateString("imageUrl", imageUrl);
			/* 这里要改年龄,从现在的年份得到 */
			Date nowTime = new Date();
			int age = nowTime.getYear() + 1900 - birthYear;
			modifyMemberResultSet.updateInt("age", age);
			modifyMemberResultSet.updateRow();
			modifyMemberResultSet.close();
			modifyMemberStmt.close();
			conn.close();
		}
	}
   
	/**
	 * 查询所有会员信息<br>
	 * 只有管理员有此权限
	 * @param privilege
	 * 根据权限验证是否为管理员
	 * @return
	 * 返回查询结果集<br>
	 * 不满足条件返回为空
	 * @throws SQLException
	 * @throws NamingException 
	 */
    public ResultSet lookupUser(int privilege) throws SQLException, NamingException{
    	if(privilege==0) {//管理员
    		Connection conn = null;
    		conn = (jdbcPool.getDataSource()).getConnection();
    		if(conn!=null){
    			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
    			ResultSet memeberDetailsResultSet =  stmt.executeQuery("select * from user ");
    			stmt.close();
    			conn.close();
    			return memeberDetailsResultSet;
    		}
    	}
    	return null;	
    }
    /**
     * 得到权限，0：管理员  1：普通会员
     * @param name
     * @return
     * @throws SQLException
     * @throws NamingException
     */
    public int getUserPrivilege(String name) throws SQLException, NamingException{
    	ResultSet memberDetails = getUserDetails(name);
    	memberDetails.next();
    	return memberDetails.getInt("privilege");
    }
    
    /**
     * 查看会员账户余额
     * @param name
     * 会员名
     * @return
     * 返回账户余额
     * @throws SQLException
     * @throws NamingException 
     */
    public int getUserAccount(String name) throws SQLException, NamingException{
    	ResultSet memberDetails = getUserDetails(name);
    	memberDetails.next();
    	return memberDetails.getInt("account");
    }
  
    /**
     * 取得会员的分数
     * @param name
     * @return
     * @throws SQLException
     * @throws NamingException
     */
    public int getUserPoints(String name) throws SQLException, NamingException{
    	ResultSet memberDetails = getUserDetails(name);
    	memberDetails.next();
    	return memberDetails.getInt("points");
    }
    /**
     * 得到会员等级
     * @param name
     * @return
     * @throws SQLException
     * @throws NamingException
     */
    
    public int getUserLevel(String name) throws SQLException, NamingException{
    	int points = getUserPoints(name);//会员级别为积分除以300
    	return points/300;
    }
    /**
     * 得到会员应享受的折扣
     * @param name
     * @return
     * @throws SQLException
     * @throws NamingException
     */
    public int getUserDiscount(String name) throws SQLException, NamingException{
    	int level = getUserLevel(name);
        if(level<3)  //0,1,2等级均不打折
        	return 10;
        else if(level<5)
        	return 9;
        else if(level<7)
        	return 8;
        else if(level<10)
        	return 7;
        else
        	return 6;  //最多打六折
    	
    }
    /**
     * 修改会员的账户余额
     * @param name
     * 会员名
     * @param increAccount
     * 该变量，若为负数，则表示余额减少该量的绝对值
     * @throws SQLException 
     */
    public void modifyUserAccount(String name,int increAccount) throws SQLException{
    	PreparedStatement pst = null;
    	Connection conn = null;
	    try {
			conn = (jdbcPool.getDataSource()).getConnection();
			if(conn!=null){
			pst = conn.prepareStatement("update user set account = account + ? where name = ");
			pst.setInt(1,increAccount);
			pst.setString(2, name);
			pst.executeUpdate();
			}
			} catch (SQLException e) {
				conn.close();
				e.printStackTrace();
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				conn.close();
				e.printStackTrace();
			}
	    conn.close();
    }
    /**
     * 修改会员积分
     * @param name
     * @param increPoint
     * @throws SQLException
     */
    public void modifyUserPoints(String name,int increPoint) throws SQLException{
    	PreparedStatement pst = null;
    	Connection conn = null;
	    try {
			conn = (jdbcPool.getDataSource()).getConnection();
			if(conn!=null){
			pst = conn.prepareStatement("update user set points = points + ? where name = ");
			pst.setInt(1,increPoint);
			pst.setString(2, name);
			pst.executeUpdate();
			}
			} catch (SQLException e) {
				conn.close();
				e.printStackTrace();
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				conn.close();
				e.printStackTrace();
			}
	    conn.close();
    }
  

    
}

