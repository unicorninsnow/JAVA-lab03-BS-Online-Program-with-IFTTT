package weibo4j.examples.timeline;

import weibo4j.Timeline;
import weibo4j.examples.oauth2.Log;
import weibo4j.model.Status;
import weibo4j.model.WeiboException;

public class UpdateStatus {

	public static void main(String[] args) {
		String access_token = "2.00RAQc4CY8c1gD23bc2310085O2MAC";
		String statuses = "!";
		Timeline tm = new Timeline();
		tm.client.setToken(access_token);
		try {
			Status status = tm.UpdateStatus(statuses);
			//Log.logInfo(status.toString());
		} catch (WeiboException e) {
			e.printStackTrace();
		}	}

}
