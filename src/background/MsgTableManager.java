package background;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.NamingException;

import background.UserTableManager;
import background.jdbcPool;

/**
 *消息表管理类
 *@author mzs<br>
 *<h2>created on 2013-12-7
 */
public class MsgTableManager {
	
	/**
	 * 无参构造函数
	 */
     public MsgTableManager(){
    	 /****************************
    	  * 
    	  */
    	  
    	 
     }  
     
     /**
      * 修改消息内容，管理员有权限
      * @param  privilege
      * 0:管理员，1：普通会员
      * @param messageID
      * 消息ID，不可以更改，作列表的value属性值
      * @param type
      * 消息内型 0：公告  1：站内信
      * @param content
      * 消息内容
      * @param receiver
      * 接受者，若是公告，则置空
      * @return
      * 返回-1：收信人不存在，返回1，修改成功，返回0，无权限(普通会员),返回2，出错
      * @throws SQLException
     * @throws NamingException 
      */
     public int modifyAdminSendMessage(int privilege,String messageID,int type,String content,String receiver) throws SQLException, NamingException{
     	if(privilege==0){//管理员	
     		if(UserTableManager.getUserDetails(receiver)==null) //收信人不存在
     			return -1;
     		Connection conn = null;
     		conn = jdbcPool.getDataSource().getConnection();
     		if(conn!=null){
     			Statement modifyAdminSendMessageStmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
     			ResultSet modifyMessageResultSet = modifyAdminSendMessageStmt.executeQuery("select * from message where messageID = "+messageID+"");
     			modifyMessageResultSet.next();
     			modifyMessageResultSet.updateInt("type",type);
     			modifyMessageResultSet.updateString("content", content);
     			modifyMessageResultSet.updateString("receiver",receiver);
     			//将修改写到数据库
     			modifyMessageResultSet.updateRow();
     			conn.close();
     			return 1;
     		}
     		return 2;
     	}
     	else
     	    return 0;
		
     }
     
     /**
      * 根据消息ID得到某一条消息的具体内容
      * @param messageID
      * @return
      * 查询结果集
      * @throws SQLException
     * @throws NamingException 
      */
     public ResultSet getMessageDetails(String messageID) throws SQLException, NamingException{
    	Connection conn = null;
  		conn = jdbcPool.getDataSource().getConnection();
  		if(conn!=null){
  			PreparedStatement pstmt = conn.prepareStatement("select * from message where messageID = ?");
  			pstmt.setString(1, messageID);
  			ResultSet messageDetailsResultSet = pstmt.getResultSet();
  			pstmt.close();
  			conn.close();
  			return messageDetailsResultSet;
  		}
  		return null;
     }
     
     /**
      * 修改消息
      * @param privilege
      * 权限 管理员：1，普通会员 ：0
      * @param messageID
      * @return
      * 修改成功返回true，会员无权修改，返回false
      * @throws SQLException
     * @throws NamingException 
      */
     public boolean deleteAdminSendMessage(int privilege, String messageID) throws SQLException {
     	if(privilege==0){
     		Connection conn = null;
      		try {
				conn = jdbcPool.getDataSource().getConnection();
      		    if(conn!=null){
      		    	Statement deleteAdminSendMessageStmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
      		    	deleteAdminSendMessageStmt.executeUpdate("delete from task where taskID = "+messageID+"");
      		    	
 		        }
      		}
      		//操作不成功要进行回滚
      		catch (SQLException e) {
				// TODO Auto-generated catch block
      			try {
					conn.rollback();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				e.printStackTrace();
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				try {
					conn.rollback();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				e.printStackTrace();
			}
     	     finally{
     	    	conn.close();
     	     }
  		    	return true;
     	  
     
        }
         return false;
     }
     
     
     /**
      * 管理员取得其自己发布的消息，包括站内信和公告，
      * 出于隐私管理考虑，我们认为普通会员间的通信管理员是不可以查看的
      * @param privilege
      * 权限，管理员0,普通会员1
      * @param String name
      * 会员(管理员)名称
      * @return
      * 返回查询得到的结果集
      * @throws SQLException
      * @throws NamingException 
      */
     public ResultSet getAdminSendMessage(int privilege,String name) throws SQLException, NamingException{
     	if(privilege==0){
     		Connection conn = null;
      		conn = jdbcPool.getDataSource().getConnection();
      		if(conn!=null){
     		PreparedStatement pstmt = conn.prepareStatement("select * from message where sender = ?");
     		pstmt.setString(1, name);
     		ResultSet adminMessageResultSet = pstmt.getResultSet();
     		pstmt.close();
     		conn.close();
     		return adminMessageResultSet;
      		}
     		return null;
     	}
     	else
     	   return null;
     }
    
     
     /**
 	 * 得到公告
 	 * 
 	 * @return 返回结果集
 	 * @throws SQLException
     * @throws NamingException 
 	 */
 	public ResultSet getNoticeMessage() throws SQLException, NamingException {
 		Connection conn = null;
  		conn = jdbcPool.getDataSource().getConnection();
  		if(conn!=null){
  			PreparedStatement stmt = conn.prepareStatement("select * from message where type = 0");
  			ResultSet noticeMessageResultSet = stmt.getResultSet();
  			return noticeMessageResultSet;
  		}
  		return null;
 	}
     
 	/**
	 * 发布公告，只有管理员才有此项权限
	 * @return
	 * 管理员发布返回true
	 * 当普通会员试图发布时，会返回false
	 * @throws SQLException
	 */
	public boolean postNoticeMessage(int privilege,String name,String messageContent) throws SQLException {
		if(privilege==0) {
			Connection conn = null;
	  		try {
				conn = jdbcPool.getDataSource().getConnection();
	  		    if(conn!=null){
			    PreparedStatement pst = conn.prepareStatement("insert into message (type,content,sender,receiver) values (?,?,?,?)");
			    pst.setInt(1,0);
			    //0为够公告，1为站内
			    pst.setString(2,messageContent);
			    pst.setString(3,name);
			    pst.setString(3,null);
			    pst.close();
	  		    }
	  		    }
	  		//如果插入不成功，要进行回滚
	  	  catch (NamingException e) {
					// TODO Auto-generated catch block
					try {
						conn.rollback();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					e.printStackTrace();
		  }
	     	catch (SQLException e) {
			// TODO Auto-generated catch block
  			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
	   	}
			  finally{  
				  conn.close();
			  }
			    return true;
	    } 	
	     return false;
	}
	
	/**
	 * 发送站内信，会员和管理员都具有次权限
	 * @param receiverName
	 * 接受者名字
	 * @param messageContent
	 * 站内信内容
	 * @return
	 * 如果发送成功，则返回true
	 * 如果发送对象名称不存在，则发送失败，返回true
	 * @throws SQLException
	 * @throws NamingException 
	 */
	public boolean sendInstationMessage(String name,String receiverName,String messageContent) throws SQLException, NamingException{
		if(UserTableManager.getUserDetails(receiverName)!=null){  //收信存在
			Connection conn = null;
	  		try {
				conn = jdbcPool.getDataSource().getConnection();
	  		    if(conn!=null){
			PreparedStatement pst = conn.prepareStatement("insert into message (type,content,sender,receiver) values (?,?,?,?)");
			pst.setInt(1,1);
			pst.setString(2,messageContent);
			pst.setString(3,name);
			pst.setString(3,receiverName);
			pst.close();
	  		    }
	  		}
	  	//操作不成功要进行回滚
      		catch (SQLException e) {
				// TODO Auto-generated catch block
      			try {
					conn.rollback();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				e.printStackTrace();
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				try {
					conn.rollback();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				e.printStackTrace();
			}
     	     finally{
     	    	conn.close();
     	     }
  		    	return true;
     	  
     
        }
     
		return false;
	}
	
	
	
	
	/**
	 * 取得站内信
	 * 
	 * @param name
	 *            发给name用户的
	 * @return 返回查询得到的结果集
	 * @throws SQLException
	 * @throws NamingException 
	 */
	public ResultSet getInstationMessage(String name) throws SQLException, NamingException {
		/* type为1为站内信，0为公告 */
		Connection conn = null;
			conn = jdbcPool.getDataSource().getConnection();
  		    if(conn!=null){
		       PreparedStatement pstmt = conn.prepareStatement("select * from message where receiver = ? and type = 1");
		       pstmt.setString(1, name);
		      ResultSet instationMessageResultSet = pstmt.getResultSet();
	        	return instationMessageResultSet;
  		    }
  		    return null;
	}
	
}
