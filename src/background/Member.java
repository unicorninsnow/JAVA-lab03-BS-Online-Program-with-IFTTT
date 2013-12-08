
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

/**会员类，分装一些数据和实现方法细节
 * 
 * @author: 孟占帅
 * created on 2013-11-27
 */
public class Member {
	private String name;
	private int privilege; // 0为管理员，1为普通会员
	static Map<String, ArrayList> runningTaskPoolHashMap = new HashMap<String, ArrayList>();


   
   /**
    *查询未被添加的事件   
    * @param type
    * 0：THIS，1：THAT
    * @return
    * 返回查询结果
    * @throws SQLException 
    */
    public ResultSet getNonAddedEvent(int type) throws SQLException{
    	PreparedStatement pstmt = eventConnection.prepareStatement("select * from event where type = ? and isAdded = 0");
    	pstmt.setInt(1, type);
    	pstmt.close();
    	return pstmt.getResultSet();
    }
    /**
     * 添加事件，只显示可添加的事件
     * @param type
     * 0：THIS 1：THAT
     * @param selectedEventType
     * 选中的eventType，这个作为列表value属性值
     * @return
     * 若添加成功返回true（管理员操作），否则返回false(会员操作)
     * @throws SQLException
     */
    public boolean addEvent(int type,int selectedEventType) throws SQLException{
    	if(privilege==0) {//管理员
    		PreparedStatement nonAddedPstmt = eventConnection.prepareStatement("select * from event where type = ? and isAdded = 0 and eventType = ? ");
    		nonAddedPstmt.setInt(1, type);
    		nonAddedPstmt.setInt(2,selectedEventType);
        	ResultSet nonAddedEvent = nonAddedPstmt.getResultSet();
        	nonAddedPstmt.close();
        	PreparedStatement addEventPstmt = eventConnection.prepareStatement("update event set isAdded = 1 where eventType = ?");
        	addEventPstmt.setString(1,nonAddedEvent.getString("eventType"));
        	addEventPstmt.executeUpdate();
        	addEventPstmt.close();
        	return true;
    	}
    	return false;
    }
    /**
     * 删除TIHS，THAT事件，如有会员有该事件任务正在运行，则通知该会员停止任务
     * @param type
     * 0：THIS 1：THAT
     * @param selectedEventType
     * 选中的eventType，这个作为列表value属性值
     * @return
     * 删除成功返回true，会员操作则返回false
     * @throws SQLException
     */
    public boolean deleteEvent(int type,int selectedEventType) throws SQLException{
    	if(privilege==0) {//管理员
    		PreparedStatement addedPstmt = eventConnection.prepareStatement("select * from event where type = ? and isAdded = 1 and eventType = ? ");
    		addedPstmt.setInt(1, type);
    		addedPstmt.setInt(2,selectedEventType);
        	ResultSet addedEvent = addedPstmt.getResultSet();
        	addedPstmt.close();
        	/*去内存中正在运行的任务中找使用该事件的用户*/
        	Iterator iter = runningTaskPoolHashMap.entrySet().iterator();
        	while (iter.hasNext()) {
              	Map.Entry entry = (Map.Entry) iter.next();
                String key = (String) entry.getKey();
                ArrayList runningTaskLst  = (ArrayList) entry.getValue();
                ResultSet runningTaskSet = null; 
                for(int i = 0;i < runningTaskLst.size(); i ++){  
                	runningTaskSet = getTaskDetails((String)runningTaskLst.get(i));
                    if(runningTaskSet!=null){
                    	if(runningTaskSet.getString("taskBuilder").equals(key)){
                    		String messageContent = "管理员消息：触发事件即将被删除，请您停止以下任务的运行:"+runningTaskSet.getString("taskName")+"\nID:"+runningTaskSet.getString("taskName");
                    		sendInstationMessage(key,messageContent);
                    	}
                    } 
                }  
                runningTaskSet.close();
        	}
        	
        	PreparedStatement deleteEventPstmt = eventConnection.prepareStatement("update event set isAdded = 0 where eventType = ?");
        	deleteEventPstmt.setString(1,addedEvent.getString("eventType"));
        	deleteEventPstmt.executeUpdate();
        	addedEvent.close();
        	deleteEventPstmt.close();
        	return true;
    	}
    	return false;
    	
    }
    
  
   
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
