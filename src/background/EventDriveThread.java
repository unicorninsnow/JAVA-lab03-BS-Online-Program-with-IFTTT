
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
 * 事件出发线程<br>
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
	private Session sessionImap;
	
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
		taskID = this.taskID;
		name = this.name;
		taskResultSet = TaskTableManager.getTaskDetails(taskID);
		taskResultSet.next();
		Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());//可能一编译就好了
		final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
		//Imap收邮件
		Properties propsImap = System.getProperties();
		propsImap.setProperty("mail.imap.socketFactory.class", SSL_FACTORY);
		propsImap.setProperty("mail.imap.socketFactory.fallback", "false");
		propsImap.setProperty("mail.imap.port", "993");
		propsImap.setProperty("mail.imap.socketFactory.port", "993");
		sessionImap = Session.getDefaultInstance(propsImap, null);	
	}

	public ResultSet getTaskResultSet() {
		return taskResultSet;
	}
	public void setTaskResultSet(ResultSet taskResultSet) {
		this.taskResultSet = taskResultSet;
	}

	/*发邮件*/
	private void sendGMail() throws MessagingException, SQLException {
		/* 发邮件 */
		Properties propsSmtp = System.getProperties();
		propsSmtp.setProperty("mail.smtp.host", "smtp.gmail.com");
		final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
		propsSmtp.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
		propsSmtp.setProperty("mail.smtp.socketFactory.fallback", "false");
		propsSmtp.setProperty("mail.smtp.port", "465");
		propsSmtp.setProperty("mail.smtp.socketFactory.port", "465");
		propsSmtp.put("mail.smtp.auth", "true");
		Session sessionSmtp = Session.getInstance(propsSmtp);
		String srcMailBox = taskResultSet.getString("taskTHISMailBox");
		String srcMailPassWd = taskResultSet.getString("taskTHISMailPassWd");
		URLName urln = new URLName("smtp", "smtp.gmail.com", 465, null,srcMailBox,srcMailPassWd);
		Transport store = sessionSmtp.getTransport(urln);
		store.connect();// 链接服务器
		Message message = new MimeMessage(sessionSmtp);
		message.addRecipients(Message.RecipientType.TO, InternetAddress.parse(taskResultSet.getString("taskTHATMailBox")));
		// Message.RecipientType.TO：普通的“收件人”
		message.setText(taskResultSet.getString("content"));// 设置邮件内容，纯文本
		message.setSubject(taskResultSet.getString("mailSubject"));
		message.setSentDate(new Date());
		store.sendMessage(message, message.getAllRecipients());
		store.close();
		
	}
	
	
	/**
	 * 发微博
	 * @throws HttpException
	 * @throws IOException
	 * @throws SQLException
	 */
    private void updateWeibo() throws HttpException, IOException, SQLException{
			String access_token = getWeiboToken.getToken(taskResultSet.getString("taskTHATWeiboID"),taskResultSet.getString("taskTHATWeiboPassWd"));
			Timeline tm = new Timeline();
			tm.client.setToken(access_token);
			try {
				Status status = tm.UpdateStatus(taskResultSet.getString("content"));
			} catch (WeiboException e) {
				e.printStackTrace();
			}

	}
    /**
     * 监听Gmail邮箱新邮件的到来
     * @return
     * 新邮件到来返回true，否则一直等
     * @throws SQLException
     */
    private boolean listenGmailBox() throws SQLException{
    	    String srcMailBox = taskResultSet.getString("taskTHISMailBox");
		    String srcMailPassWd = taskResultSet.getString("taskTHISMailPassWd");
			URLName urln = new URLName("imap", "imap.gmail.com", 993, null,
					srcMailBox, srcMailPassWd);
			Store store = null;
			int count = 0;
			Message[] messages = null;
			try{
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
     */
    
	private boolean checkWeiboContent() throws SQLException, HttpException, IOException, WeiboException, InterruptedException{
    	    String weiboID = taskResultSet.getString("taskTHISWeiboID");
    	    String weiboPassWd = taskResultSet.getString("taskTHISWeiboPassWd");
    	    String checkContent = taskResultSet.getString("weiboCheckCon"); 
    		String access_token =  getWeiboToken.getToken(weiboID,weiboPassWd);
    		Timeline tm = new Timeline();
    		tm.client.setToken(access_token);
    		StatusWapper status = tm.getUserTimeline();
    		while(true){
    			if(status.getTotalNumber()>0){
    				List<Status> s = status.getStatuses();
    			    if(s.get(0).getText().contains(checkContent))
    				   break;
    			 
    	        }
    	    //10秒去读一次微博内容
   			 Thread.sleep(10000);   
    		}
    		return true;
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
		  if(thisType==0) //定时任务
		  {   
			  
			   long clockTime = getClockMillis();
			   if(checkClockTime(clockTime)){
			      Thread.sleep(clockTime);
			      if(thatType==3) //定时发邮件
			      {
			    	  sendGMail();
			      }
				  if(thatType==4)//定时发微博
				  {
					  updateWeibo();
				  }
		  }
		  else if(thisType==1){//收到邮件作为触发源
			  if(listenGmailBox()){
				  //收到邮件发邮件到指定邮箱
				  if(thatType==3)
				  {
					  sendGMail();
				  }
				  //收到邮件发微博
				  if(thatType==4)
				  {
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
			   
		}
		  
		UserTableManager userManage = new UserTableManager();
		int increAccount = 1000*userManage.getUserDiscount(name)/10;
		userManage.modifyUserAccount(name,-increAccount);
		userManage.modifyUserPoints(name, 100);//执行一个任务，则积分加100
		//记录消费记录
		String format = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date now = new Date();
		String  nowString = sdf.format(now);// 返回规定格式的字符串，字符串表示时间
		PaymentLogTableManager paymentLogManager = new PaymentLogTableManager();
		paymentLogManager.addPaymentLog(name, thisType, thatType,nowString,increAccount);
		  
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




				
}
	
	


