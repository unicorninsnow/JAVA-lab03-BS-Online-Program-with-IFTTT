
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

/**会员类，分装一些数据和实现方法细节
 * 
 * @author: 孟占帅
 * created on 2013-11-27
 */
public class Member {
	private String name;
	private int privilege; // 0为管理员，1为普通会员
	//从可删除任务中选中的任务索引，顺序和数据库中一样，以此为据，去数据库找对应任务删除
	private int selectedDeleteTaskIndex; 
	private int selectedModifyTaskIndex;
	/* 连到四个table的连接 */
	private Connection memberConnection;
	private Connection messageConnection;
	private Connection paymentLogConnection;
	private Connection taskConnection;
	private Connection eventConnection;
	/* hashmap,在服务器内存中，用来存储所有会员正在运行的任务,第一个是会员名称（ID），第二个是任务ID */
	static Map<String, ArrayList> runningTaskPoolHashMap = new HashMap<String, ArrayList>();

	/**
	 * constructor function
	 * 
	 * @param name
	 *            jsp登陆界面传进来的登录名，唯一值可作ID
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public Member(String name) throws ClassNotFoundException, SQLException {
		initializeJdbc();
		this.name = name;
		ResultSet memberDeatails = getMemberDetails(name);
		privilege = memberDeatails.getInt("privilege");

	}
	public  ArrayList getRunningTaskHeader(){
		return runningTaskPoolHashMap.get(name);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPrivilege() {
		return privilege;
	}

	public Connection getMemberConnection() {
		return memberConnection;
	}

	public Connection getMessageConnection() {
		return messageConnection;
	}

	public Connection getPaymentLogConnection() {
		return paymentLogConnection;
	}

	public Connection getTaskConnection() {
		return taskConnection;
	}

	/**
	 * 初始化数据库连接， 建立到不同表的连接
	 * 
	 * @throws ClassNotFoundException
	 */
	public void initializeJdbc() throws SQLException, ClassNotFoundException {
		String driver = "com.maysql.jdbc.Driver";
		Class.forName(driver);
		memberConnection = DriverManager.getConnection("");// url要改一下
		messageConnection = DriverManager.getConnection("");
		paymentLogConnection = DriverManager.getConnection("");
		taskConnection = DriverManager.getConnection("");
		eventConnection = DriverManager.getConnection("");
	}

	/**
	 * 根据会员名查询会员信息
	 * 
	 * @param name
	 *            会员名称
	 * @return 查询得到的结果集
	 * @throws SQLException
	 * */
	public ResultSet getMemberDetails(String name) throws SQLException {
		PreparedStatement pstmt = memberConnection
				.prepareStatement("select * from member where name = ?");
		pstmt.setString(1, name);
		ResultSet memberDetailsResultSet = pstmt.getResultSet();
		return memberDetailsResultSet;
	}

	/**
	 * 查询得到消费记录
	 * 
	 * @param name
	 *            会员名
	 * @return 返回查询得到的结果集
	 * @throws SQLException
	 */
	public ResultSet getPaymentLog(String name) throws SQLException {
		PreparedStatement pstmt = paymentLogConnection
				.prepareStatement("select * from paymentLog where name = ?");
		pstmt.setString(1, name);
		ResultSet paymentLogResultSet = pstmt.getResultSet();
		return paymentLogResultSet;
	}

	/**
	 * 取得站内信
	 * 
	 * @param name
	 *            发给name用户的
	 * @return 返回查询得到的结果集
	 * @throws SQLException
	 */
	public ResultSet getInstationMessage(String name) throws SQLException {
		/* type为1为站内信，0为公告 */
		PreparedStatement pstmt = messageConnection
				.prepareStatement("select * from message where receiver = ? and type = 1");
		pstmt.setString(1, name);
		ResultSet instationMessageResultSet = pstmt.getResultSet();
		return instationMessageResultSet;
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
	 */
	public boolean sendInstationMessage(String receiverName,String messageContent) throws SQLException{
		if(getMemberDetails(receiverName)!=null){  //收信人不存在
			PreparedStatement pst = messageConnection.prepareStatement("insert into message (type,content,sender,receiver) values (?,?,?,?)");
			pst.setInt(1,1);
			pst.setString(2,messageContent);
			pst.setString(3,name);
			pst.setString(3,receiverName);
			pst.close();
			return true;
		}
		return false;
	}
	/**
	 * 发布公告，只有管理员才有此项权限
	 * @return
	 * 管理员发布返回true
	 * 当普通会员试图发布时，会返回false
	 * @throws SQLException
	 */
	public boolean postNoticeMessage(String messageContent) throws SQLException{
		if(privilege==0) {
			PreparedStatement pst = messageConnection.prepareStatement("insert into message (type,content,sender,receiver) values (?,?,?,?)");
			pst.setInt(1,0);//0为够公告，1为站内
			pst.setString(2,messageContent);
			pst.setString(3,name);
			pst.setString(3,null);
			pst.close();
			return true;
		}
		return false;
	}
	
	/**
	 * 得到公告
	 * 
	 * @return 返回结果集
	 * @throws SQLException
	 */
	public ResultSet getNoticeMessage() throws SQLException {
		PreparedStatement stmt = messageConnection
				.prepareStatement("select * from message where type = 0");
		ResultSet noticeMessageResultSet = stmt.getResultSet();
		return noticeMessageResultSet;
	}

	/**
	 * 修改资料，会员名，权限，级别，金钱，注册时间不可修改
	 * 
	 * @param passWd
	 *            密码
	 * @param sex
	 *            性别,0为男性，1为女性
	 * @param birthYear
	 *            出生年数,年龄根据出生日期得到
	 * @param imageUrl
	 *            头像
	 * @throws SQLException
	 */
	public void modifyMemberData(String passWd, int sex, int birthYear,
			int birthMonth, int birthDay, String imageUrl) throws SQLException {
		Statement modifyMemberStmt = memberConnection.createStatement(
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		ResultSet modifyMemberResultSet = modifyMemberStmt
				.executeQuery("select * from where name = " + name + "");
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
	}

	/**
	 * 新建任务，从jsp文件读取输入的字符串，存储到数据库 
	 * 如果没有对应字符串，则在jsp文件中赋值为""或null,否则要写很多种类的新建任务的函数
	 * taskID由后台根据taskName得到
	 * 
	 */
	public void createNewTask(String taskName, String taskBuilder,
			String taskBuildTime, String taskDeadTime, int taskTHISType,
			int taskTHATType, String taskTHISMailBox,
			String taskTHISMailPassWd, String taskTHISWeiboID,
			String taskTHISWeiboPassWd, String taskTHATMailBox,
			String taskTHATMailPassWd, String taskTHATWeiboID,
			String taskTHATWeiboPassWd) {
		String taskID = UUID.randomUUID().toString();// 用来生成号称全球唯一的ID
		PreparedStatement newTaskPst;
		try {
			newTaskPst = taskConnection
					.prepareStatement("insert into task (taskID,taskName,taskBuilder,taskBuildTime,taskDeadTime,taskTHISType,taskTHATType,taskTHISMailBox,taskTHISMailPassWd,taskTHISWeiboID,taskTHISWeiboPassWd,taskTHATMailBox,taskTHATMailPassWd,taskTHISWeiboID,taskTHISWeiboPassWd) values (???????????????)");
			newTaskPst.setString(1, taskID);
			newTaskPst.setString(2, taskName);
			newTaskPst.setString(3, taskBuilder);
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

		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 查看某会员所有任务
	 * @return
	 * 返回该会员所有任务的结果集
	 * @throws SQLException
	 */
	public ResultSet lookupTask() throws SQLException{
		Statement lookupTaskStmt = taskConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		ResultSet lookupTaskResultSet =  lookupTaskStmt.executeQuery("select * from task where taskBuilder = "+name+"");
		lookupTaskStmt.close();
		return lookupTaskResultSet;
		
	}
	/**
	 * 查看某一个任务全部信息
	 * @param taskID
	 * 任务ID
	 * @return
	 * 返回结果集
	 * @throws SQLException
	 */
	/*
	 * 我觉得可以考虑把任务的ID作列表或者下拉此单的value属性，
	 * 这样比单纯列表和数据库顺序一样安全性高点
	 * 我函数是这样写的，不行再改吧
	 * 
	 */
	public ResultSet getTaskDetails(String taskID) throws SQLException{
		Statement getTaskDetailsStmt = taskConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		ResultSet taskDetailsResultSet =  getTaskDetailsStmt.executeQuery("select * from task where taskID = "+taskID+"");
		getTaskDetailsStmt.close();
		return  taskDetailsResultSet;
	}
	/**
	 * 删除任务
	 * @param taskID
	 * @return
	 * 若删除成功，则返回true，若任务正在运行，返回false,无法删除
	 * @throws SQLException
	 */
	public boolean deleteTask(String taskID) throws SQLException{
		ArrayList runningTaskPool = getRunningTaskHeader();
		if(runningTaskPool!=null){
			//如果正在运行，则不可以删除
		    if(runningTaskPool.contains(taskID))
			    return false;
		}
		Statement deleteTaskStmt = taskConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		deleteTaskStmt.executeUpdate("delete from task where taskID = "+taskID+"");
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
	 * @param taskTHATMailPassWd
	 * @param taskTHATWeiboID
	 * @param taskTHATWeiboPassWd
	 * @return
	 * 修改成功返回true，若任务正在运行无法修改返回false
	 * @throws SQLException
	 */
	public boolean modifyTask(String taskID,String taskName, 
			 String taskDeadTime, int taskTHISType,
			int taskTHATType, String taskTHISMailBox,
			String taskTHISMailPassWd, String taskTHISWeiboID,
			String taskTHISWeiboPassWd, String taskTHATMailBox,
			String taskTHATMailPassWd, String taskTHATWeiboID,
			String taskTHATWeiboPassWd) throws SQLException {
		ArrayList runningTaskPool = getRunningTaskHeader();
		if(runningTaskPool!=null){
			//如果正在运行，则不可以修改
		    if(runningTaskPool.contains(taskID))
			    return false;
		}
		Statement modifyTaskStmt = taskConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		ResultSet modifyTaskResultSet = modifyTaskStmt.executeQuery("select * from task where taskID = "+taskID+"");
		modifyTaskResultSet.next();
		modifyTaskResultSet.updateString("taskName",taskName);
		modifyTaskResultSet.updateString("taskDeadTime", taskDeadTime);
		modifyTaskResultSet.updateInt("taskTHISType",taskTHISType);
		modifyTaskResultSet.updateInt("taskTHATType",taskTHATType);
		modifyTaskResultSet.updateString("taskTHISMailBox",taskTHISMailBox);
		modifyTaskResultSet.updateString("taskTHISMailPassWd",taskTHISMailPassWd);
		modifyTaskResultSet.updateString(" taskTHISWeiboID", taskTHISWeiboID);
		modifyTaskResultSet.updateString(" taskTHISWeiboPassWd",taskTHISWeiboPassWd);
		modifyTaskResultSet.updateString("taskTHATMailBox",taskTHATMailBox);
		modifyTaskResultSet.updateString("taskTHATMailPassWd",taskTHATMailPassWd);
		modifyTaskResultSet.updateString(" taskTHATWeiboID", taskTHATWeiboID);
		modifyTaskResultSet.updateString(" taskTHATWeiboPassWd",taskTHATWeiboPassWd);
		//将修改写到数据库
		modifyTaskResultSet.updateRow();
		return true;
	}
	public boolean runTask(String taskID){
		
	}
	
	public boolean stopTask(String taskID){
		//test
	}
	/**
	 * 查询所有会员(管理员)的信息
	 * @return
	 * 返回结果集
	 * @throws SQLException
	 */
    public ResultSet lookupMember() throws SQLException{
    	if(privilege==0) {//管理员
    		Statement stmt = memberConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
    		ResultSet memeberDetailsResultSet =  stmt.executeQuery("select * from member ");
    		stmt.close();
    		return memeberDetailsResultSet;
    	}
    	return null;	
    }
   /**
    * 根据会员名查询会员等级
    * @param name
    * 会员名
    * @return
    * 返回会员等级
    * @throws SQLException
    */
    public int getMemberLevel(String name) throws SQLException{
    	ResultSet memberDetails = getMemberDetails(name);
    	return memberDetails.getInt("level");
    }
    /**
     * 查看会员账户余额
     * @param name
     * 会员名
     * @return
     * 返回账户余额
     * @throws SQLException
     */
    public int getMemberAccount(String name) throws SQLException{
    	ResultSet memberDetails = getMemberDetails(name);
    	return memberDetails.getInt("account");
    }
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
    /**
     * 管理员取得其自己发布的消息，包括站内信和公告，
     * 出于隐私管理考虑，我们认为普通会员间的通信管理员是不可以查看的
     * @return
     * 返回查询得到的结果集
     * @throws SQLException
     */
    public ResultSet getAdminSendMessage() throws SQLException{
    	if(privilege==0){
    		PreparedStatement pstmt = messageConnection
    				.prepareStatement("select * from message where sender = ?");
    		pstmt.setString(1, name);
    		ResultSet adminMessageResultSet = pstmt.getResultSet();
    		pstmt.close();
    		return adminMessageResultSet;
    		
    	}
    	else
    	   return null;
    }
    /**
     * 修改消息内容，管理员有权限
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
     */
    public int modifyAdminSendMessage(String messageID,int type,String content,String receiver) throws SQLException{
    	if(privilege==0){//管理员
    		if(getMemberDetails(receiver)==null) //收信人不存在
    			return -1;
    		Statement modifyAdminSendMessageStmt = messageConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
    		ResultSet modifyMessageResultSet = modifyAdminSendMessageStmt.executeQuery("select * from message where messageID = "+messageID+"");
    		modifyMessageResultSet.next();
    		modifyMessageResultSet.updateInt("type",type);
    		modifyMessageResultSet.updateString("content", content);
    		modifyMessageResultSet.updateString("receiver",receiver);
    		//将修改写到数据库
    		modifyMessageResultSet.updateRow();
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
     */
    public ResultSet getMessageDetails(String messageID) throws SQLException{
    	PreparedStatement pstmt = messageConnection
				.prepareStatement("select * from message where messageID = ?");
		pstmt.setString(1, messageID);
		ResultSet messageDetailsResultSet = pstmt.getResultSet();
		pstmt.close();
		return messageDetailsResultSet;
    }
    /**
     * 修改消息
     * @param messageID
     * @return
     * 修改成功返回true，会员无权修改，返回false
     * @throws SQLException
     */
    public boolean deleteAdminSendMessage(String messageID) throws SQLException{
    	if(privilege==0){
    	Statement deleteAdminSendMessageStmt = messageConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		deleteAdminSendMessageStmt.executeUpdate("delete from task where taskID = "+messageID+"");
		return true;
    	}
    	return false;
    
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
