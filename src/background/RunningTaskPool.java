package background;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.naming.NamingException;

/**
 * 管理正在运行的任务<br>
 * javabean中scope范围设置为Application
 * <p>
 * runningTaskPool:
 * 管理正在运行的任务，根据用户名称查找其对应的任务ID
 * <p>
 * runningTaskThreadPool:
 * 管理正在运行任务的线程，根据taskID查找对应的线程
 * @author mzs
 *created  on  2013-12-8
 */
public class RunningTaskPool {
	static Map<String, ArrayList> runningTaskPool = new HashMap<String, ArrayList>();;
    static Map<String,Thread> runningTaskThreadPool = new HashMap<String, Thread>();
    /***
     * 
     * 这里最好再定义一个线程池
     * 
     * 
     */
    
    
    /**
     * 无参默认构造方法
     */
	/*public RunningTaskPool(){
		runningTaskPool = new HashMap<String, ArrayList>();
		runningTaskThreadPool = new HashMap<String, Thread>();
	}*/
	
	
	/**
	 * 根据任务ID找到对应线程
	 * @param taskID
	 * 任务ID
	 * @return
	 * 返回找到的线程，可能为null
	 */
	private Thread getTaskThread(String taskID){
		Thread taskThreadTemp = (EventDriveThread)runningTaskThreadPool.get(taskID);
        return taskThreadTemp;
		
	}
	
	
	
	/**
	 * 在运行任务线程中添加新的线程
	 * @param taskID
	 * @param taskThread
	 */
	private synchronized void  addTaskThread(String taskID,Thread taskThread){
		runningTaskThreadPool.put(taskID,taskThread);	
	}
	
	/**
	 * 用户把任务加到运行队列中
	 * @param name
	 * 用户名
	 * @param taskID
	 * 任务ID
	 * @param taskThread
	 * 对应线程
	 */
	private synchronized void addRunningTask(String name,String taskID,Thread taskThread){
		ArrayList runningTaskList = (ArrayList)runningTaskPool.get(name);
		if(runningTaskList == null){ //该用户尚未运行过任务
			runningTaskList = new ArrayList();
			runningTaskPool.put(name,runningTaskList);
		}
		runningTaskList.add(taskID);
		addTaskThread(taskID,taskThread);
	}
	
	
	/**
	 * 根据任务ID删除其对应的线程
	 * @param taskID
	 * @return
	 * 成功返回true，失败(未找到该线程)返回false
	 */
	private synchronized boolean deleteTaskThread(String taskID){
		//根据hashMap中key同则覆盖
		if(getTaskThread(taskID)!=null){
			runningTaskThreadPool.put(taskID, null);
			System.out.println("deletTasjThread");
			return true;
		}
		return false;
	}
	
	/**
	 * 从运行任务存储空间删除某个的任务<br>
	 * 包括删除线程
	 * @param name
	 * @param taskID
	 * @return
	 * 删除成功返回true，失败则返回false
	 */
	private synchronized boolean deleteRunningTask(String name,String taskID){
		ArrayList runningTaskList = (ArrayList)runningTaskPool.get(name);
		if(runningTaskList == null) //该用户尚未运行过任务
			return false;
		runningTaskList.remove(taskID);
	    return 	deleteTaskThread(taskID);
		
	}
	/**
	 * 判断一个线程是否在运行
	 * @param taskID
	 * @return
	 */
	public synchronized static boolean isRunning(String taskID){
		Thread tempThread = (EventDriveThread)runningTaskThreadPool.get(taskID);
		if(tempThread==null)
			return false;
		return true;
		
	}
	/**
	 * 某一用户开始运行任务
	 * @param name
	 * 用户名
	 * @param taskID
	 * 任务ID
	 * @return
	 * 若任务正在运行，则返回false，否则运行任务，返回true
	 * @throws SQLException
	 * @throws NamingException
	 * @throws ClassNotFoundException 
	 */
	public void startTask(String name,String taskID) throws SQLException, NamingException, ClassNotFoundException{
		//请在开始前，调用函数isRunning判断某一任务是否正在运行
		//同时判断余额是否大于等于1000，因为执行一个任务扣费1000
		EventDriveThread taskThread = new EventDriveThread(taskID,name);
		addRunningTask(name,taskID,taskThread);		
		taskThread.start();
		
	}
	/**
	 * 停止某一任务的运行
	 * @param name
	 * 会员名
	 * @param taskID
	 * 任务ID
	 */
	public  void stopTask(String name,String taskID){
		EventDriveThread taskThread =(EventDriveThread)getTaskThread(taskID);
		System.out.println("daoci1111");
		if(taskThread!=null){
			System.out.println("daoci2222");
			deleteRunningTask(name,taskID);
		}
	}
		
	public static void main(String args[]) throws ClassNotFoundException, SQLException, NamingException{
		RunningTaskPool test = new RunningTaskPool();
		test.startTask("mzs", "dingshifayoujian");
		
	}
}
