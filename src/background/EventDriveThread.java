
package background;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.security.Security;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.List;

import weibo4j.Timeline;
import weibo4j.examples.oauth2.Log;
import weibo4j.model.Status;
import weibo4j.model.StatusWapper;
import weibo4j.model.WeiboException;

import javax.mail.AuthenticationFailedException;
import javax.mail.FetchProfile;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.URLName;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.naming.NamingException;
import javax.swing.JOptionPane;

import org.apache.commons.httpclient.HttpException;


/**
 * 事件触发线程<br>
 * thatType <b>   3:发邮件   4：发微博<br>
 * thatType <b>   0：定时  1： 收到邮件    2：最新微博含指定内容
 * 
 * @author  孟占帅
 * created on 2013-12-1
 */
public class EventDriveThread extends  Thread {
	//线程对应的任务ID
	private String taskID = null;
	private String name = null;  //因为要记录消费记录的拥有者
	private ResultSet taskResultSet = null;
	/*收邮件,连接邮箱*/
	private Session sessionImap = null;
	
	/**
	 * 构造函数
	 * @param taskID
	 * 任务ID
	 * @param name
	 * 会员名，因为要记录消费记录，所以添加此成员
	 * @throws SQLException
	 * @throws NamingException
	 * @throws ClassNotFoundException 
	 */
	public EventDriveThread (String taskID,String name) throws SQLException, NamingException, ClassNotFoundException{
		this.taskID = taskID;
	    this.name = name;
		taskResultSet = TaskTableManager.getTaskDetails(taskID);
		taskResultSet.next();
		
	}

	public ResultSet getTaskResultSet() {
		return taskResultSet;
	}
	public void setTaskResultSet(ResultSet taskResultSet) {
		this.taskResultSet = taskResultSet;
	}

	/*发邮件*/
	private void sendGMail() throws MessagingException, SQLException, ClassNotFoundException, NamingException {
		/* 发邮件 */
		System.out.println("fayoujian");
		try{
		Properties propsSmtp = System.getProperties();
		propsSmtp.setProperty("mail.smtp.host", "smtp.gmail.com");
		final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
		propsSmtp.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
		propsSmtp.setProperty("mail.smtp.socketFactory.fallback", "false");
		propsSmtp.setProperty("mail.smtp.port", "465");
		propsSmtp.setProperty("mail.smtp.socketFactory.port", "465");
		propsSmtp.put("mail.smtp.auth", "true");
		Session sessionSmtp = Session.getInstance(propsSmtp);
		String srcMailBox = taskResultSet.getString("srcMailBox");
		taskResultSet.beforeFirst();
		if(taskResultSet.next()){
		String srcMailPassWd = taskResultSet.getString("srcMailPassWd");
		URLName urln = new URLName("smtp", "smtp.gmail.com", 465, null,srcMailBox,srcMailPassWd);
		Transport store = sessionSmtp.getTransport(urln);
		store.connect();// 链接服务器
		System.out.println("连接");
		Message message = new MimeMessage(sessionSmtp);
		message.addRecipients(Message.RecipientType.TO, InternetAddress.parse(taskResultSet.getString("dstMailBox")));
		// Message.RecipientType.TO：普通的“收件人”
		message.setText(taskResultSet.getString("content"));// 设置邮件内容，纯文本
		message.setSubject(taskResultSet.getString("mailSubject"));
		message.setSentDate(new Date());
		store.sendMessage(message, message.getAllRecipients());
		store.close();
		}
		}
		//邮箱密码和账号不匹配，则提示
		catch (AuthenticationFailedException e){
			String taskName = new TaskTableManager().getTaskName(taskID);
			String content = "系统通知:您名为 "+taskName+" 的任务邮箱账号和密码认证错误，请重新检查确认";
			new MsgTableManager().sendInstationMessage("admin", name, content);
			return;
		}
		//else
		//给用户发信
		
	}
	
	
	/**
	 * 发微博
	 * @throws HttpException
	 * @throws IOException
	 * @throws SQLException
	 * @throws NamingException 
	 * @throws ClassNotFoundException 
	 */
    private void updateWeibo() throws HttpException, IOException, SQLException, ClassNotFoundException, NamingException{
			String access_token = getWeiboToken.getToken(taskResultSet.getString("updateWeiboID"),taskResultSet.getString("updateWeiboPassWd"));
			Timeline tm = new Timeline();
			System.out.println("access_token"+access_token);
			if(access_token!=null){   //输入的账号正确
			tm.client.setToken(access_token);
			try {
				Status status = tm.UpdateStatus(taskResultSet.getString("content"));
			} catch (WeiboException e) {
				e.printStackTrace();
			}
			}
			//账号和密码不匹配，发消息告诉用户
			else{
				String taskName = new TaskTableManager().getTaskName(taskID);
				String content = "系统通知:您名为 "+taskName+" 的任务微博账号和密码认证错误，请重新检查确认";
				new MsgTableManager().sendInstationMessage("admin", name, content);
				return;
			}
				

	}
    /**
     * 监听Gmail邮箱新邮件的到来
     * @return
     * 新邮件到来返回true，否则一直等
     * @throws SQLException
     */
    private boolean listenGmailBox() throws SQLException{
    	    //连接邮箱
    	    Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());//可能一编译就好了
		    final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
		    //Imap收邮件
		    Properties propsImap = System.getProperties();
		    propsImap.setProperty("mail.imap.socketFactory.class", SSL_FACTORY);
		    propsImap.setProperty("mail.imap.socketFactory.fallback", "false");
		    propsImap.setProperty("mail.imap.port", "993");
		    propsImap.setProperty("mail.imap.socketFactory.port", "993");
		    sessionImap = Session.getDefaultInstance(propsImap, null);	
		    try{
    	    String srcMailBox = taskResultSet.getString("srcMailBox");
		    String srcMailPassWd = taskResultSet.getString("srcMailPassWd");
			URLName urln = new URLName("imap", "imap.gmail.com", 993, null,
					srcMailBox, srcMailPassWd);
			Store store = null;
			int count = 0;
			Message[] messages = null;
		    store = sessionImap.getStore(urln);
			Folder inbox = null;
			store.connect();
			inbox = store.getFolder("INBOX");
			inbox.open(Folder.READ_ONLY);
			FetchProfile profile = new FetchProfile();
			profile.add(FetchProfile.Item.ENVELOPE);
			messages = inbox.getMessages();
			inbox.fetch(messages, profile);
			count = messages.length;
			//一直等待新邮件的到来
            while (messages.length <= count) {
            	messages = inbox.getMessages();
            	inbox.fetch(messages, profile);
            	System.out.println("sleeppppppppp");
            	Thread.sleep(15000);
			}
			}
		    catch (NoSuchProviderException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					}
	        catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}// 每隔15秒去读邮箱邮件数
			catch (MessagingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			//如果收到新邮件
		    return true;
    	    	
    }
    
    /**
     * 检查微博账户否含指定内容
     * @return
     * 如果检查到则返回true，否则一直检查
     * @throws SQLException
     * @throws HttpException
     * @throws IOException
     * @throws WeiboException
     * @throws InterruptedException
     * @throws NamingException 
     * @throws ClassNotFoundException 
     */
    
	private boolean checkWeiboContent() throws SQLException, HttpException, IOException, WeiboException, InterruptedException, ClassNotFoundException, NamingException{
    	    String weiboID = taskResultSet.getString("updateWeiboID");
    	    String weiboPassWd = taskResultSet.getString("updateWeiboPassWd");
    	    String checkContent = taskResultSet.getString("weiboCheckCon"); 
    	    System.out.println("checkContent"+checkContent);
    		String access_token =  getWeiboToken.getToken(weiboID,weiboPassWd);
    		if(access_token!=null){
    		Timeline tm = new Timeline();
    		tm.client.setToken(access_token);
    		while(true){
    			
        		StatusWapper status = tm.getUserTimeline();
    			if(status.getTotalNumber()>0){
    				List<Status> s = status.getStatuses();
    				System.out.println(s.get(0).getText());
			    	System.out.println(checkContent);
    			    if(s.get(0).getText().contains(checkContent))
    				    break;
    			 
    	        }
    			System.out.println("sleeeeeee");
    	    //10秒去读一次微博内容
   			 Thread.sleep(10000);   
    		}
    		return true;
    		}
    		else
    		{
    			String taskName = new TaskTableManager().getTaskName(taskID);
				String content = "系统通知:您名为 "+taskName+" 的任务监听微博账号和密码认证错误，请重新检查确认";
				new MsgTableManager().sendInstationMessage("admin", name, content);
				return false;
    		}
    			
    }
	

	/**
	 * 计算出定时任务的时间间隔
	 * @return
	 * 返回毫秒数
	 * @throws SQLException 
	 * @throws ParseException 
	 */
	private long getClockMillis() throws ParseException, SQLException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date stopDate = sdf.parse(taskResultSet.getString("taskDeadTime"));
		Calendar stopTime = Calendar.getInstance();
		stopTime.setTime(stopDate);
		stopTime.set(Calendar.MILLISECOND, 0);
		Calendar startPoint = Calendar.getInstance();
		Date now = new Date();
		startPoint.setTime(now);
		long timeTotal =stopTime.getTimeInMillis() - startPoint.getTimeInMillis();
		return timeTotal;

	}
    /**
     * 检查定时范围是否合法<br>
     * 该函数可以在jsp界面调用进行时间检查
     * @return
     */
    public boolean checkClockTime(long clockTime){
    	if(clockTime<0||clockTime>Long.MAX_VALUE)
    		return false;
    	return true;
    
    }
    
	   
	public void run() {
		try {
		  int thisType = taskResultSet.getInt("taskTHISType");
		  int thatType = taskResultSet.getInt("taskTHATType");
		  System.out.println(thisType+"  "+thatType);
		  if(thisType==0) //定时任务
		  {   
			  
			   long clockTime = getClockMillis();
			   if(checkClockTime(clockTime)){
			      Thread.sleep(clockTime);
			      if(thatType==3) //定时发邮件
			      {   
			    	  sendGMail();
			    	  System.out.println("发完");
			      }
				  if(thatType==4)//定时发微博
				  {
					  updateWeibo();
					  System.out.println("发完");
				  }
		  }
			   else
				   new MsgTableManager().sendInstationMessage("admin",name, "admin:您的定时范围出错，请检查确认");
		  }
		  else if(thisType==1){//收到邮件作为触发源
			  System.out.println(thisType+"youjian");
			  if(listenGmailBox()){
				  //收到邮件发邮件到指定邮箱
				  System.out.println("1");
				  if(thatType==3)
				  {
					  sendGMail();
				  }
				  //收到邮件发微博
				  if(thatType==4)
				  {   
					  System.out.println("4");
					  updateWeibo();
				  }
			  }
		  }  
		  else if(thisType==2){//监听微博，若收到带指定内容的微博，则执行指定任务
			  if(checkWeiboContent()){
					  //监听成功发邮件到指定邮箱
					  if(thatType==3) 
						  sendGMail();
					  //监听成功发微博
					  if(thatType==4)
						  updateWeibo();
				  }
			  }
			     
		UserTableManager userManage = new UserTableManager();
		int increAccount = 1000*userManage.getUserDiscount(name)/10;
		System.out.println("zhekou    "+increAccount);
		userManage.modifyUserAccount(name,(-1)*increAccount);
		userManage.modifyUserPoints(name, 100);//执行一个任务，则积分加100
		//记录消费记录
		String format = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date now = new Date();
		String  nowString = sdf.format(now);// 返回规定格式的字符串，字符串表示时间
		PaymentLogTableManager paymentLogManager = new PaymentLogTableManager();
		paymentLogManager.addPaymentLog(name, thisType, thatType,nowString,increAccount);
		//任务运行完成，从hash pool删除
		RunningTaskPool deleteRanThread = new RunningTaskPool();
		deleteRanThread.stopTask(name, taskID);
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WeiboException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

     
//	public static void main(String args[]) throws ClassNotFoundException, SQLException, NamingException{
//		EventDriveThread test = new EventDriveThread("dingshifayoujian","mzs");
//	 	test.start();
//		
//	}


				
}
	
	


