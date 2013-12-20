
package background;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Date;
import java.util.UUID;

import javax.naming.NamingException;

/**
 * 事件管理类，包括添加删除this和that事件
 * @author 孟占帅
 * created on 2013-12-9 
 *
 */
public class EventTableManager {
	
   
   /**
    *查询未被添加的事件   
    * @param type
    * 0：THIS，1：THAT
    * @return
    * 返回查询结果
    * @throws SQLException 
    * @throws NamingException 
    * @throws ClassNotFoundException 
    */
    public ResultSet getNonAddedEvent(int type) throws SQLException, NamingException, ClassNotFoundException{
    	Connection conn = null;
		//conn = jdbcPool.getDataSource().getConnection();
    	conn = jdbcPool.getDataSource();
    	PreparedStatement pstmt = conn.prepareStatement("select * from event where type = ? and isAdded = 0");
    	pstmt.setInt(1, type);
    	System.out.println("aaaaaaaaaaaaaaaaaaa");
    	return pstmt.executeQuery();
    	
    }
    
    /**
     * 查询已被添加的事件，新建任务时候使用
     * @param type
     * 0：THIS   1：THAT
     * @return
     * @throws SQLException
     * @throws NamingException
     * @throws ClassNotFoundException
     */
    public ResultSet getAddedEvent(int type) throws SQLException, NamingException, ClassNotFoundException{
    	Connection conn = null;
		//conn = jdbcPool.getDataSource().getConnection();
    	conn = jdbcPool.getDataSource();
    	PreparedStatement pstmt = conn.prepareStatement("select * from event where type = ? and isAdded = 1");
    	pstmt.setInt(1, type);
    	System.out.println("aaaaaaaaaaaaaaaaaaa");
    	return pstmt.executeQuery();
    	
    }
    
    /**
     * 添加事件，只显示可添加的事件
     * @param type
     * 0：THIS 1：THAT
     * @param selectedEventType
     * 选中的eventType，这个作为列表value属性值
     * @return
     * 若添加成功返回true（管理员操作），否则返回false(会员操作,或数据库错误)
     * @throws SQLException
     */
    public boolean addEvent(int privilege,int type,int selectedEventType){
    	if(privilege==0) {//管理员
    		Connection conn = null;
    	try {
			//conn = jdbcPool.getDataSource().getConnection();
    	    conn = jdbcPool.getDataSource();	
    		PreparedStatement nonAddedPstmt = conn.prepareStatement("select * from event where type = ? and isAdded = 0 and eventType = ? ");
    		nonAddedPstmt.setInt(1, type);
    		nonAddedPstmt.setInt(2,selectedEventType);
        	ResultSet nonAddedEvent = nonAddedPstmt.executeQuery();
        	if(nonAddedEvent.next()){
        	PreparedStatement addEventPstmt = conn.prepareStatement("update event set isAdded = 1 where eventType = ?");
        	addEventPstmt.setString(1,nonAddedEvent.getString("eventType"));
        	addEventPstmt.executeUpdate();
        	addEventPstmt.close();
        	conn.close();
    		return true;
        	}
        	else 
        		return false;
    	    	}/* catch (NamingException e) {
				// TODO Auto-generated catch block
    	    	conn.rollback(); // 更新不成功进行回滚
				e.printStackTrace();
			} */catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
				try {
					conn.rollback();
					conn.close();
					return false;
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					
					try {
						
						conn.rollback();
						conn.close();
					} catch (SQLException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					return false;
				}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					try {
						conn.rollback();
						conn.close();
						return false;
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					e.printStackTrace();
				}
        return true;
    	}
        else
        	return false;
    }
    
    
    
    ////////////////////////////////没检查////////////////////////////////////////////////
    /**
     * 删除TIHS，THAT事件，如有会员有该事件任务正在运行，则通知该会员停止任务
     * @param type
     * 0：THIS 1：THAT
     * @param selectedEventType
     * 选中的eventType，这个作为列表value属性值
     * @return
     * 删除成功返回true，会员操作则返回false
     * @throws NamingException 
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    public boolean deleteEvent(String name,int type,int selectedEventType) throws NamingException, ClassNotFoundException, SQLException{
    	UserTableManager userPri;
	    userPri = new UserTableManager();
    	int privilege = userPri.getUserPrivilege(name);
    	if(privilege==0) {//管理员
    		Connection conn = null;
		   // conn = jdbcPool.getDataSource().getConnection();
    		 try {
				conn = jdbcPool.getDataSource();
    		PreparedStatement addedPstmt = conn.prepareStatement("select * from event where type = ? and isAdded = 1 and eventType = ? ");
    		addedPstmt.setInt(1, type);
    		addedPstmt.setInt(2,selectedEventType);
        	ResultSet addedEvent = addedPstmt.executeQuery();//得到所有可以删除的事件
            if(addedEvent.next()){
        	/*去内存中正在运行的任务中找使用该事件的用户*/
        	Iterator iter =  RunningTaskPool.runningTaskPool.entrySet().iterator();
        	while (iter.hasNext()) {
              	Map.Entry entry = (Map.Entry) iter.next();
                String key = (String) entry.getKey();//这里的key是任务拥有者的名称
                ArrayList runningTaskLst  = (ArrayList) entry.getValue();
                ResultSet runningTaskSet = null; 
                for(int i = 0;i < runningTaskLst.size(); i++){  
                	runningTaskSet = TaskTableManager.getTaskDetails((String)runningTaskLst.get(i));
                    if(runningTaskSet.next()){
                    	if(runningTaskSet.getString("taskBuilder").equals(key)){
                    		String messageContent = "管理员消息：触发源事件即将被删除，请您停止以下任务的运行:"+runningTaskSet.getString("taskName")+"\nID:"+runningTaskSet.getString("taskName");
                    		MsgTableManager sendInMs = new MsgTableManager();
                    		//发信人   收信人   站内信内容
                    		sendInMs.sendInstationMessage(name,key,messageContent);
                    	}
                    } 
                }  
                runningTaskSet.close();
        	}
            }
        	PreparedStatement deleteEventPstmt = conn.prepareStatement("update event set isAdded = 0 where eventType = ?");
        	deleteEventPstmt.setString(1,addedEvent.getString("eventType"));
        	deleteEventPstmt.executeUpdate();
        	addedEvent.close();
        	deleteEventPstmt.close();
        	conn.close();
    		 } catch (SQLException e) {
 				// TODO Auto-generated catch block
    			 try {
					conn.rollback();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
 				e.printStackTrace();
 			}
    	    return true;
    	
    	
    }  
    	return false;
 }
    
    public static void main(String args[]) throws ClassNotFoundException, SQLException, NamingException{
    	EventTableManager test = new EventTableManager();
    	test.addEvent(0,1,4);
    	
    	
    }
    
}
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

