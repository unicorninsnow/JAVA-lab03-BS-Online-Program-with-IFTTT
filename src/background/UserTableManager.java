package background;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

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
	 * @throws ClassNotFoundException 
	 * */
	public static ResultSet getUserDetails(String name) throws SQLException, NamingException, ClassNotFoundException  {
		 Connection conn = null;
		// conn = ((DataSource)jdbcPool.getDataSource()).getConnection();
		conn = jdbcPool.getDataSource();
		PreparedStatement pstmt = conn.prepareStatement("select * from user where name = ?");
		pstmt.setString(1, name);
		ResultSet memberDetailsResultSet = pstmt.executeQuery();
		return memberDetailsResultSet;
		
	}
	
	/**
	 * 注册函数，然后写
	 */
	
//	public void userRegister(String name,String passWd,int privilege,int account,String ){
//		
//		
//		
//		
//	}
	
	/**
	 * 修改会员资料
	 * @param name
	 * @param passWd
	 * @param sex
	 * @param birthDate
	 * @param imageUrl
	 * 头像
	 * @param email
	 * @return
	 * 修改结束返回true
	 * 若未找到对应会员，则返回false
	 * @throws SQLException
	 * @throws NamingException
	 * @throws ClassNotFoundException
	 * @throws ParseException
	 */
	public synchronized boolean modifyUserData(String name, String passWd, int sex, String birthDate, String imageUrl,String email) throws SQLException, NamingException, ClassNotFoundException, ParseException {
		Connection conn = null;
		//conn = (jdbcPool.getDataSource()).getConnection();
		if(!getUserDetails(name).next())
			return false;  //查无此人，错误
		conn = jdbcPool.getDataSource();
		Statement modifyMemberStmt = conn.createStatement(
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		ResultSet modifyMemberResultSet = modifyMemberStmt
				.executeQuery("select * from user where name = '" + name +"'");
		modifyMemberResultSet.next();
		modifyMemberResultSet.updateString("passWd", passWd);
		modifyMemberResultSet.updateInt("sex", sex);
		modifyMemberResultSet.updateString("birthDate", birthDate);
		modifyMemberResultSet.updateString("email", email);
		modifyMemberResultSet.updateString("imageUrl", imageUrl);
			/* 这里要改年龄,从现在的年份得到 */
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date birthTime = sdf.parse(birthDate);
		Date nowTime = new Date();
		int age = nowTime.getYear() - birthTime.getYear();
		modifyMemberResultSet.updateInt("age", age);
		modifyMemberResultSet.updateRow();
		modifyMemberResultSet.close();
		modifyMemberStmt.close();
		conn.close();
		return true;
	}
   
	
	//////////////////////////已检查，可以执行
	
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
	 * @throws ClassNotFoundException 
	 */
    public ResultSet lookupUser(int privilege) throws SQLException, NamingException, ClassNotFoundException{
    	if(privilege==0) {//管理员
    		Connection conn = null;
    	//	conn = (jdbcPool.getDataSource()).getConnection();
    		conn = jdbcPool.getDataSource();
    		Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
    		ResultSet memeberDetailsResultSet =  stmt.executeQuery("select * from user ");
    		return memeberDetailsResultSet;
    		
    	}
    	return null;	
    }
    
    
    
    /**
     * 得到权限，0：管理员  1：普通会员
     * @param name
     * @return
     * 若找到，则返回对应的权限，否则返回-1
     * @throws SQLException
     * @throws NamingException
     * @throws ClassNotFoundException 
     */
    public int getUserPrivilege(String name) throws SQLException, NamingException, ClassNotFoundException{
    	ResultSet memberDetails = getUserDetails(name);
    	if(!memberDetails.next())
    		return -1;    
    	return memberDetails.getInt("privilege");
    }
    
    /***********************可以使用**********************/
    
    
    /**
     * 查看会员账户余额
     * @param name
     * 会员名
     * @return
     * 返回账户余额
     * 查无此人，返回-1
     * @throws SQLException
     * @throws NamingException 
     * @throws ClassNotFoundException 
     */
    public int getUserAccount(String name) throws SQLException, NamingException, ClassNotFoundException{
    	ResultSet memberDetails = getUserDetails(name);
    	if(!memberDetails.next())
    		return -1;
    	return memberDetails.getInt("account");
    }
  
    /**
     * 取得会员的分数
     * @param name
     * @return
     * 查无此人，返回-1
     * @throws SQLException
     * @throws NamingException
     * @throws ClassNotFoundException 
     */
    public int getUserPoints(String name) throws SQLException, NamingException, ClassNotFoundException{
    	ResultSet memberDetails = getUserDetails(name);
    	if(!memberDetails.next())
    		return -1;
    	return memberDetails.getInt("points");
    }
    /**
     * 得到会员等级
     * @param name
     * @return
     *查无此人，返回-1
     * @throws SQLException
     * @throws NamingException
     * @throws ClassNotFoundException 
     */
    
    public int getUserLevel(String name) throws SQLException, NamingException, ClassNotFoundException{
    	int points = getUserPoints(name);//会员级别为积分除以300
    	if(points==-1)
    		return -1;
    	return points/300;
    }
    /**
     * 得到会员应享受的折扣
     * @param name
     * @return
     * @throws SQLException
     * @throws NamingException
     * @throws ClassNotFoundException 
     */
    public int getUserDiscount(String name) throws SQLException, NamingException, ClassNotFoundException{
    	int level = getUserLevel(name);
    	if(level==-1)
    		return -1;
    	else if(level<3)  //0,1,2等级均不打折
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
     * *************可以用  ***********************************************/
    
    
    /**
     * 修改会员的账户余额
     * @param name
     * 会员名
     * @param increAccount
     * 该变量，若为负数，则表示余额减少该量的绝对值
     * @return
     * 修改失败返回false
     * @throws SQLException 
     * @throws ClassNotFoundException 
     */
    public void modifyUserAccount(String name,int increAccount) throws SQLException, ClassNotFoundException{
    	PreparedStatement pst = null;
    	Connection conn = null;
	    try {
			//conn = (jdbcPool.getDataSource()).getConnection();
	    	conn = jdbcPool.getDataSource();
			pst = conn.prepareStatement("update user set account = account + ? where name = ?");
			pst.setInt(1,increAccount);
			pst.setString(2, name);
			pst.executeUpdate();
			} catch (SQLException e) {
				conn.rollback();
				//System.out.println(e.getMessage());
				e.printStackTrace();
			} /*catch (NamingException e) {
				// TODO Auto-generated catch block
				conn.close();
				e.printStackTrace();
			}*/
	        finally{
	       conn.close();
	      	
	       }
    }
    /**
     * 修改会员积分
     * @param name
     * @param increPoint
     * @return
     * 修改失败返回false
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    public void modifyUserPoints(String name,int increPoint) throws SQLException, ClassNotFoundException{
    	PreparedStatement pst = null;
    	Connection conn = null;
	    try {
			//conn = (jdbcPool.getDataSource()).getConnection();
	    	conn = jdbcPool.getDataSource();
			pst = conn.prepareStatement("update user set points = points + ? where name = ?");
			pst.setInt(1,increPoint);
			pst.setString(2, name);
			pst.executeUpdate();
			} catch (SQLException e) {
				conn.rollback();
				e.printStackTrace();
			} /*catch (NamingException e) {
				// TODO Auto-generated catch block
				conn.close();
				e.printStackTrace();
			}*/
	   finally{
		   conn.close();
	   }
    }
  
    
    public static void main(String args[]) throws ClassNotFoundException, SQLException, NamingException, ParseException{
    	//Scanner input = new Scanner(System.in);
    	UserTableManager temp =new UserTableManager();
    	temp.modifyUserPoints("mzs", 1000);
    	temp.modifyUserPoints("mzs", -300);
    	temp.modifyUserAccount("niu",20);
    	temp.modifyUserAccount("n",20);
    	//System.out.println((UserTableManager.getUserDetails(name)).next());
    	
    }

    
}

