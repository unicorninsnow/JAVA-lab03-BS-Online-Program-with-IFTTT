package background;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import javax.naming.NamingException;

/**
 * 管理task表
 * @author mzs
 *created on 2013-12-6
 */
public class TaskTableManager {
	/**
	 * 新建任务，从jsp文件读取输入的字符串，存储到数据库 
	 * 如果没有对应字符串，则在jsp文件中赋值为""或null,否则要写很多种类的新建任务的函数
	 * taskID由后台根据taskName得到
	 * 
	 */
	public void createNewTask(String taskName, String taskBuilder,
		    String taskDeadTime, int taskTHISType,
			int taskTHATType, String taskTHISMailBox,
			String taskTHISMailPassWd, String taskTHISWeiboID,
			String taskTHISWeiboPassWd, String taskTHATMailBox,
			String taskTHATMailPassWd, String taskTHATWeiboID,
			String taskTHATWeiboPassWd) {
		String taskID = UUID.randomUUID().toString();// 用来生成号称全球唯一的ID
		PreparedStatement newTaskPst;
		Connection conn = null;
		try {
			conn = jdbcPool.getDataSource().getConnection();
		    if(conn!=null){
			newTaskPst = conn.prepareStatement("insert into task (taskID,taskName,taskBuilder,taskBuildTime,taskDeadTime,taskTHISType,taskTHATType,taskTHISMailBox,taskTHISMailPassWd,taskTHISWeiboID,taskTHISWeiboPassWd,taskTHATMailBox,taskTHATMailPassWd,taskTHISWeiboID,taskTHISWeiboPassWd) values (???????????????)");
			newTaskPst.setString(1, taskID);
			newTaskPst.setString(2, taskName);
			newTaskPst.setString(3, taskBuilder);
			
			
			String format = "yyyy-MM-dd HH:mm:";
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			Date now = new Date();
			String  taskBuildTime = sdf.format(now);// 返回规定格式的字符串，字符串表示时间
			newTaskPst.setString(4, taskBuildTime);
			newTaskPst.setString(5, taskDeadTime);
			newTaskPst.setInt(6, taskTHISType);
			newTaskPst.setInt(7, taskTHATType);
			newTaskPst.setString(8, taskTHISMailBox);
			newTaskPst.setString(9, taskTHISMailPassWd);
			newTaskPst.setString(10, taskTHISWeiboID);
			newTaskPst.setString(11, taskTHISWeiboPassWd);
			newTaskPst.setString(12, taskTHATMailBox);
			newTaskPst.setString(13, taskTHATMailPassWd);
			newTaskPst.setString(14, taskTHATWeiboID);
			newTaskPst.setString(15, taskTHATWeiboPassWd);
			newTaskPst.executeUpdate();
			newTaskPst.close();
		    }
		}
       //同样插入不成功要进行回滚
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	/**
	 * 查看某会员所有任务
	 * @param name
	 * 会员名称
	 * @return
	 * 返回该会员所有任务的结果集
	 * @throws SQLException
	 * @throws NamingException 
	 */
	public ResultSet lookupTask(String name) throws SQLException, NamingException{
		Connection conn = null;
		conn = jdbcPool.getDataSource().getConnection();
		    if(conn!=null){
		Statement lookupTaskStmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		ResultSet lookupTaskResultSet =  lookupTaskStmt.executeQuery("select * from task where taskBuilder = "+name+"");
		lookupTaskStmt.close();
		conn.close();
		return lookupTaskResultSet;
		    }
		 return null;
		
	}
	
	
	/**
	 * 查看某一个任务全部信息
	 * @param taskID
	 * 任务ID
	 * @return
	 * 返回结果集
	 * @throws SQLException
	 * @throws NamingException 
	 */
	/*
	 * 我觉得可以考虑把任务的ID作列表或者下拉此单的value属性，
	 * 这样比单纯列表和数据库顺序一样安全性高点
	 * 我函数是这样写的，不行再改吧
	 * 
	 */
	public static ResultSet getTaskDetails(String taskID) throws SQLException, NamingException{
		Connection conn = null;
			conn = jdbcPool.getDataSource().getConnection();
		    if(conn!=null){
		Statement getTaskDetailsStmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		ResultSet taskDetailsResultSet =  getTaskDetailsStmt.executeQuery("select * from task where taskID = "+taskID+"");
		getTaskDetailsStmt.close();
		conn.close();
		return  taskDetailsResultSet;
		    }
		    return null;
	}
	
	
	/**
	 * 删除任务
	 * @param taskID
	 * @return
	 * 若删除成功，则返回true，若任务正在运行，返回false,无法删除
	 * @throws SQLException
	 * @throws NamingException 
	 */
	public boolean deleteTask(String taskID) throws SQLException, NamingException{
		//这里要改一下，把正在运行任务和对应的线程建在这个类中
		if(RunningTaskPool.isRunning(taskID))
			return false;
		Connection conn = null;
		conn = jdbcPool.getDataSource().getConnection();
	    if(conn!=null){
		Statement deleteTaskStmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		deleteTaskStmt.executeUpdate("delete from task where taskID = "+taskID+"");
		conn.close();
	    }
		return true;
	}
	
	/**
	 * 修改任务，任务类型不同参数用不到赋值为空
	 * @param taskID
	 * @param taskName
	 * @param taskDeadTime
	 * @param taskTHISType
	 * @param taskTHATType
	 * @param taskTHISMailBox
	 * @param taskTHISMailPassWd
	 * @param taskTHISWeiboID
	 * @param taskTHISWeiboPassWd
	 * @param taskTHATMailBox
	 * @param taskTHATWeiboID
	 * @param taskTHATWeiboPassWd
	 * @content
	 *微博或者邮件的内容
	 * @mailSubject
	 * 邮件主题
	 * @weiboCheckCon
	 * 微博敏感内容
	 * @return
	 * 修改成功返回true，若任务正在运行无法修改返回false
	 * @throws SQLException
	 * @throws NamingException 
	 */
	public boolean modifyTask(String taskID,String taskName, 
			 String taskDeadTime, int taskTHISType,
			int taskTHATType, String taskTHISMailBox,
			String taskTHISMailPassWd, String taskTHISWeiboID,
			String taskTHISWeiboPassWd, String taskTHATMailBox,
			String taskTHATMailPassWd, String taskTHATWeiboID,
			String taskTHATWeiboPassWd,String content,String mailSubject,String weiboCheckCon) {
		if(RunningTaskPool.isRunning(taskID))
			return false;
		Connection conn = null;
		try {
			conn = jdbcPool.getDataSource().getConnection();
	    if(conn!=null){
		Statement modifyTaskStmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		ResultSet modifyTaskResultSet = modifyTaskStmt.executeQuery("select * from task where taskID = "+taskID+"");
		modifyTaskResultSet.next();
		modifyTaskResultSet.updateString("taskName",taskName);
		modifyTaskResultSet.updateString("taskDeadTime", taskDeadTime);
		modifyTaskResultSet.updateInt("taskTHISType",taskTHISType);
		modifyTaskResultSet.updateInt("taskTHATType",taskTHATType);
		modifyTaskResultSet.updateString("taskTHISMailBox",taskTHISMailBox);
		modifyTaskResultSet.updateString("taskTHISMailPassWd",taskTHISMailPassWd);
		modifyTaskResultSet.updateString("taskTHISWeiboID", taskTHISWeiboID);
		modifyTaskResultSet.updateString("taskTHISWeiboPassWd",taskTHISWeiboPassWd);
		modifyTaskResultSet.updateString("taskTHATMailBox",taskTHATMailBox);
		modifyTaskResultSet.updateString("taskTHATMailPassWd",taskTHATMailPassWd);
		modifyTaskResultSet.updateString("taskTHATWeiboID", taskTHATWeiboID);
		modifyTaskResultSet.updateString("taskTHATWeiboPassWd",taskTHATWeiboPassWd);
		modifyTaskResultSet.updateString("content",content);
		modifyTaskResultSet.updateString("mailSubject", mailSubject);
		modifyTaskResultSet.updateString("weiboCheckCon", weiboCheckCon);
		//将修改写到数据库
		modifyTaskResultSet.updateRow();
		conn.close();
	    }
	    } catch (SQLException e) {
			// TODO Auto-generated catch block
	    	try {
				conn.rollback(); //操作失败回滚
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return true;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
