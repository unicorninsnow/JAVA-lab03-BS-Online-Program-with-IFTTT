package background;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.UUID;

import javax.naming.NamingException;

import org.json.JSONException;

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
	/**
	 * 
	 * @param taskName
	 * @param taskBuilder
	 * @param taskDeadTime
	 * @param taskTHISType
	 * @param taskTHATType
	 * @param srcMailBox
	 * @param srcMailPassWd
	 * @param updateWeiboID
	 * @param updateWeiboPassWd
	 * @param dstMailBox
	 * @param listenWeiboID
	 * @param listenWeiboPassWd
	 * @param content
	 * @param mailSubject
	 * @param weiboCheckCon
	 */
	public void createNewTask(String taskName, String taskBuilder,
		    String taskDeadTime, int taskTHISType,
			int taskTHATType, String srcMailBox,
			String srcMailPassWd, String updateWeiboID,
			String updateWeiboPassWd, String dstMailBox,
			String listenWeiboID,String listenWeiboPassWd,String content,String mailSubject,String weiboCheckCon
			) {
		    String taskID = UUID.randomUUID().toString();// 用来生成号称全球唯一的ID
		    PreparedStatement newTaskPst;
		    Connection conn = null;
		    try {
			//conn = jdbcPool.getDataSource().getConnection();
			conn = jdbcPool.getDataSource();
			newTaskPst = conn.prepareStatement("insert into task (taskID,taskName,taskBuilder,taskBuildTime,taskDeadTime,taskTHISType,taskTHATType,srcMailBox,srcMailPassWd,updateWeiboID,updateWeiboPassWd,dstMailBox,listenWeiboID,listenWeiboPassWd,content,mailSubject,weiboCheckCon) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			newTaskPst.setString(1, taskID);
			newTaskPst.setString(2, taskName);
			newTaskPst.setString(3, taskBuilder);
			String format = "yyyy-MM-dd HH:mm";
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			Date now = new Date();
			String  taskBuildTime = sdf.format(now);// 返回规定格式的字符串，字符串表示时间
			newTaskPst.setString(4, taskBuildTime);
			newTaskPst.setString(5, taskDeadTime);
			newTaskPst.setInt(6, taskTHISType);
			newTaskPst.setInt(7, taskTHATType);
			newTaskPst.setString(8, srcMailBox);
			newTaskPst.setString(9, srcMailPassWd);
			newTaskPst.setString(10, updateWeiboID);
			newTaskPst.setString(11, updateWeiboPassWd);
			newTaskPst.setString(12, dstMailBox);
			newTaskPst.setString(13, listenWeiboID);
			newTaskPst.setString(14, listenWeiboPassWd);
			newTaskPst.setString(15, content);
			newTaskPst.setString(16, mailSubject);
			newTaskPst.setString(17, weiboCheckCon);
			newTaskPst.executeUpdate();
			newTaskPst.close();
		    
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
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
	 * @throws ClassNotFoundException 
	 */
	public ResultSet lookupTask(String name) throws SQLException, NamingException, ClassNotFoundException{
		Connection conn = null;
		//conn = jdbcPool.getDataSource().getConnection();
		conn = jdbcPool.getDataSource();
		Statement lookupTaskStmt = conn.createStatement();
		ResultSet lookupTaskResultSet =  lookupTaskStmt.executeQuery("select * from task where taskBuilder = '"+name+"' ");
		return lookupTaskResultSet;
		
	}
	
	
	/**
	 * 查看某一个任务全部信息
	 * @param taskID
	 * 任务ID
	 * @return
	 * 返回结果集
	 * @throws SQLException
	 * @throws NamingException 
	 * @throws ClassNotFoundException 
	 */
	/*
	 * 我觉得可以考虑把任务的ID作列表或者下拉此单的value属性，
	 * 这样比单纯列表和数据库顺序一样安全性高点
	 * 我函数是这样写的，不行再改吧
	 * 
	 */
	public static ResultSet getTaskDetails(String taskID) throws SQLException, NamingException, ClassNotFoundException{
		Connection conn = null;
	    //conn = jdbcPool.getDataSource().getConnection();
		conn = jdbcPool.getDataSource();
		Statement getTaskDetailsStmt = conn.createStatement();
		ResultSet taskDetailsResultSet =  getTaskDetailsStmt.executeQuery("select * from task where taskID = '"+taskID+"'");
		return  taskDetailsResultSet;

	}
	public  String  getTaskName(String taskID) throws SQLException, ClassNotFoundException, NamingException{
		ResultSet set = getTaskDetails(taskID);
		if(set.next())
			return set.getString("name");
		else
			return null;
	}
	
	/**
	 * 删除任务
	 * @param taskID
	 * @return
	 * 若删除成功，则返回true，若任务正在运行，返回false,无法删除
	 * @throws SQLException
	 * @throws NamingException 
	 * @throws ClassNotFoundException 
	 */
	public boolean deleteTask(String taskID) throws SQLException, NamingException, ClassNotFoundException{
		//这里要改一下，把正在运行任务和对应的线程建在这个类中
		if(RunningTaskPool.isRunning(taskID))
			return false;
		Connection conn = null;
		//conn = jdbcPool.getDataSource().getConnection();
		conn = jdbcPool.getDataSource();
		Statement deleteTaskStmt = conn.createStatement();
		deleteTaskStmt.executeUpdate("delete from task where taskID = '"+taskID+"'");
		conn.close();
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
	 * @throws ClassNotFoundException 
	 * @throws SQLException
	 * @throws NamingException 
	 */
	public boolean modifyTask(String taskID,String taskName, 
			 String taskDeadTime, int taskTHISType,
			int taskTHATType, String srcMailBox,
			String srcMailPassWd, String updateWeiboID,
			String updateWeiboPassWd, String dstMailBox,
			String listenWeiboID, String listenWeiboPassWd,
			String content,String mailSubject,String weiboCheckCon) {
		if(RunningTaskPool.isRunning(taskID))
			return false;
		Connection conn = null;
		try {
			//conn = jdbcPool.getDataSource().getConnection();
	    conn = jdbcPool.getDataSource();
		Statement modifyTaskStmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		ResultSet modifyTaskResultSet = modifyTaskStmt.executeQuery("select * from task where taskID = '"+taskID+"'");
		modifyTaskResultSet.next();
		modifyTaskResultSet.updateString("taskName",taskName);
		modifyTaskResultSet.updateString("taskDeadTime", taskDeadTime);
		modifyTaskResultSet.updateInt("taskTHISType",taskTHISType);
		modifyTaskResultSet.updateInt("taskTHATType",taskTHATType);
		modifyTaskResultSet.updateString("srcMailBox",srcMailBox);
		modifyTaskResultSet.updateString("srcMailPassWd",srcMailPassWd);
		modifyTaskResultSet.updateString("updateWeiboID", updateWeiboID);
		modifyTaskResultSet.updateString("updateWeiboPassWd",updateWeiboPassWd);
		modifyTaskResultSet.updateString("dstMailBox",dstMailBox);
		modifyTaskResultSet.updateString("listenWeiboID",listenWeiboID);
		modifyTaskResultSet.updateString("listenWeiboPassWd", listenWeiboPassWd);
		modifyTaskResultSet.updateString("content",content);
		modifyTaskResultSet.updateString("mailSubject", mailSubject);
		modifyTaskResultSet.updateString("weiboCheckCon", weiboCheckCon);
		//将修改写到数据库
		modifyTaskResultSet.updateRow();
		conn.close();
	   
	    } catch (SQLException e) {
			// TODO Auto-generated catch block
	    	try {
				conn.rollback(); //操作失败回滚
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		} //catch (NamingException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		//}
         catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return true;
	}
	
//	public static void  main(String args[]) throws ClassNotFoundException, SQLException, NamingException{
//		 TaskTableManager test = new  TaskTableManager();
//		 Scanner input = new Scanner(System.in);
//		 String taskName = input.next();
//		 String taskBuilder = input.next();
//		 String taskDeadTime = input.next();
//		 int taskTHISType = Integer.parseInt(input.next());
//		 int taskTHATType = Integer.parseInt(input.next());
//		 String srcMailBox = input.next();
//		 String srcMailPassWd = "";
//		 String updateWeiboID = "";
//		 String updateWeiboPassWd = "123";
//		 String dstMailBox = "";
//		 String listenWeiboID = null;
//		 String listenWeiboPassWd = null;
//		 String content = "nima";
//		 String mailSubject = ""; 
//		 String weiboCheckCon = "haha";
//		ResultSet temp = test.lookupTask("mzs");
//		temp.next();
//		System.out.println(temp.getString("taskID"));
//		test.modifyTask(temp.getString("taskID"),taskName, taskDeadTime, taskTHISType, taskTHATType, srcMailBox, srcMailPassWd, updateWeiboID, updateWeiboPassWd, dstMailBox, listenWeiboID, listenWeiboPassWd, content, mailSubject, weiboCheckCon);
//		
//	}
//	
//	
	
	public static void main(String args[]) throws ClassNotFoundException, SQLException, JSONException, NamingException{
		TaskTableManager  test = new TaskTableManager();
		System.out.println(ResultSet2Json.resultSetToJson(test.getTaskDetails("1e88ad78-637b-46ba-8a51-271999a7025c")));
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
