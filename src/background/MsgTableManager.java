package background;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

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
      * 返回-1：收信人不存在，返回1，修改成功，返回0，无权限(普通会员)
      * @throws SQLException
     * @throws NamingException 
     * @throws ClassNotFoundException 
      */
     public int modifyAdminSendMessage(int privilege,String messageID,int type,String content,String receiver) throws SQLException, NamingException, ClassNotFoundException{
         System.out.println(privilege);     	
    	 if(privilege==0){//管理员	
     		if(!UserTableManager.getUserDetails(receiver).next()) //收信人不存在
     			return -1;
     		Connection conn = null;
     		//conn = jdbcPool.getDataSource().getConnection();
     		conn = jdbcPool.getDataSource();
     		Statement modifyAdminSendMessageStmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
     		ResultSet modifyMessageResultSet = modifyAdminSendMessageStmt.executeQuery("select * from message where messageID = '"+messageID+"'");
     		modifyMessageResultSet.next();
     		modifyMessageResultSet.updateInt("type",type);
     		modifyMessageResultSet.updateString("content", content);
     		modifyMessageResultSet.updateString("receiver",receiver);
     		//最新改动时间
     		String format = "yyyy-MM-dd HH:mm:ss";
    		SimpleDateFormat sdf = new SimpleDateFormat(format);
    		Date now = new Date();
    		String  nowString = sdf.format(now);
     		modifyMessageResultSet.updateString("lastModifyTime",nowString);
     		//将修改写到数据库
     		modifyMessageResultSet.updateRow();
     		System.out.println("nimadan");
     		modifyMessageResultSet.close();
     		conn.close();
     		return 1;
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
     * @throws ClassNotFoundException 
      */
     public ResultSet getMessageDetails(String messageID) throws SQLException, NamingException, ClassNotFoundException{
    	Connection conn = null;
  		//conn = jdbcPool.getDataSource().getConnection();
    	conn = jdbcPool.getDataSource();
  		PreparedStatement pstmt = conn.prepareStatement("select * from message where messageID = ?");
  		pstmt.setString(1, messageID);
  		ResultSet messageDetailsResultSet = pstmt.executeQuery();
  		return messageDetailsResultSet;
     }
     
     /**
      * 删除消息
      * @param privilege
      * 权限 管理员：1，普通会员 ：0
      * @param messageID
      * @return
      * 删除成功返回true，会员无权删除，返回false
      * @throws SQLException
     * @throws NamingException 
      */
     public boolean deleteAdminSendMessage(int privilege, String messageID) throws SQLException {
     	if(privilege==0){
     		Connection conn = null;
      		try {
				//conn = jdbcPool.getDataSource().getConnection();
      			 conn = jdbcPool.getDataSource();
      		    	Statement deleteAdminSendMessageStmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
      		    	deleteAdminSendMessageStmt.executeUpdate("delete from message where messageID = '"+messageID+"'"); 
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
			} /*catch (NamingException e) {
				// TODO Auto-generated catch block
				try {
					conn.rollback();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				e.printStackTrace();
			} */catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
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
      * 管理员名称
      * @return
      * 返回查询得到的结果集
      * 无查询权限返回null
      * @throws SQLException
      * @throws NamingException 
     * @throws ClassNotFoundException 
      */
     public ResultSet getAdminSendMessage(int privilege,String name) throws SQLException, NamingException, ClassNotFoundException{
     	if(privilege==0){
     		Connection conn = null;
      	//	conn = jdbcPool.getDataSource().getConnection();
     		 conn = jdbcPool.getDataSource();
     		PreparedStatement pstmt = conn.prepareStatement("select * from message where sender = ?");
     		pstmt.setString(1, name);
     		ResultSet adminMessageResultSet = pstmt.executeQuery();
     		return adminMessageResultSet;
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
     * @throws ClassNotFoundException 
 	 */
 	public ResultSet getNoticeMessage() throws SQLException, NamingException, ClassNotFoundException {
 		Connection conn = null;
  		//conn = jdbcPool.getDataSource().getConnection();
 		 conn = jdbcPool.getDataSource();
  		PreparedStatement stmt = conn.prepareStatement("select * from message where type = 0");
  		ResultSet noticeMessageResultSet = stmt.executeQuery();
  		return noticeMessageResultSet;
 	}
     
 	/**
	 * 发布公告，只有管理员才有此项权限
	 * @name
	 * 发布者的name
	 * @return
	 * 管理员发布返回true
	 * 当普通会员试图发布时，会返回false
	 * @throws SQLException
 	 * @throws ClassNotFoundException 
	 */
	public boolean postNoticeMessage(int privilege,String name,String messageContent) throws SQLException, ClassNotFoundException {
		if(privilege==0) {
			Connection conn = null;
	  		try {
				//conn = jdbcPool.getDataSource().getConnection();
	  			 conn = jdbcPool.getDataSource();
			    PreparedStatement pst = conn.prepareStatement("insert into message (messageID,type,content,sender,receiver,createTime,lastModifyTime) values (?,?,?,?,?,?,?)");
			    String messageID = UUID.randomUUID().toString();// 用来生成号称全球唯一的ID
			    pst.setString(1,messageID);
			    pst.setInt(2,0);
			    //0为够公告，1为站内
			    pst.setString(3,messageContent);
			    pst.setString(4,name);
			    pst.setString(5,null);
			    String format = "yyyy-MM-dd HH:mm:ss";
				SimpleDateFormat sdf = new SimpleDateFormat(format);
				Date now = new Date();
				String  nowString = sdf.format(now);
			    pst.setString(6, nowString);
			    pst.setString(7, nowString);
			    pst.executeUpdate();
			    pst.close();
	  		    }
	  		//如果插入不成功，要进行回滚
	  	 /* catch (NamingException e) {
					// TODO Auto-generated catch block
					try {
						conn.rollback();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					e.printStackTrace();
		  }*/
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
	 * @throws ClassNotFoundException 
	 */
	public boolean sendInstationMessage(String name,String receiverName,String messageContent) throws SQLException, NamingException, ClassNotFoundException{
		if(UserTableManager.getUserDetails(receiverName).next()){  //收信存在
			Connection conn = null;
	  		try {
				//conn = jdbcPool.getDataSource().getConnection();
	  			 conn = jdbcPool.getDataSource();
			PreparedStatement pst = conn.prepareStatement("insert into message (messageID,type,content,sender,receiver,createTime,lastModifyTime) values (?,?,?,?,?,?,?)");
			String messageID = UUID.randomUUID().toString();// 用来生成号称全球唯一的ID
			pst.setString(1, messageID);
			pst.setInt(2,1);
			pst.setString(3,messageContent);
			pst.setString(4,name);
			pst.setString(5,receiverName);
			String format = "yyyy-MM-dd HH:mm:ss";
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			Date now = new Date();
			String  nowString = sdf.format(now);
			pst.setString(6,nowString);
			pst.setString(7,nowString);
			pst.executeUpdate();
			pst.close();
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
			} /*catch (NamingException e) {
				// TODO Auto-generated catch block
				try {
					conn.rollback();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				e.printStackTrace();
			}*/
     	     finally{
     	    	conn.close();
     	     }
  		    	return true;
     	  
     
        }
		else
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
	 * @throws ClassNotFoundException 
	 */
	public ResultSet getInstationMessage(String name) throws SQLException, NamingException, ClassNotFoundException {
		/* type为1为站内信，0为公告 */
		Connection conn = null;
		//	conn = jdbcPool.getDataSource().getConnection();
		conn = jdbcPool.getDataSource();
	    PreparedStatement pstmt = conn.prepareStatement("select * from message where receiver = ? and type = 1");
		pstmt.setString(1, name);
		ResultSet instationMessageResultSet = pstmt.executeQuery();
	    return instationMessageResultSet;
	}
	public static void main(String args[]) throws ClassNotFoundException, SQLException, NamingException{
		MsgTableManager test = new MsgTableManager();
		test.sendInstationMessage("mzs", "niu", "Are y ");
	} 
	
	
}
