package weibo4j.examples.timeline;

import java.util.List;

import weibo4j.Timeline;
import weibo4j.examples.oauth2.Log;
import weibo4j.model.Status;
import weibo4j.model.StatusWapper;
import weibo4j.model.WeiboException;

public class GetUserTimeline {

	public static void main(String[] args) {
		String access_token = "2.00RAQc4CY8c1gD23bc2310085O2MAC";
		Timeline tm = new Timeline();
		tm.client.setToken(access_token);
		//StatusWapper statusList = tm.getU.getUserTimelineByName("a");
		try {
			StatusWapper status = tm.getUserTimeline();
			if(status.getTotalNumber()>0){
				List<Status> s = status.getStatuses();
			//for(Status s : status.getStatuses()){	
				String test = "碌碌无为";
			     System.out.println(s.get(0).getText().contains(test));
				//Log.logInfo(s.toString());
			}
				//System.out.println(status.getStatuses());
		//	}
		 //  System.out.println(status.getNextCursor());
			//System.out.println(status.toString());
		//	System.out.println(status.getPreviousCursor());
		//	System.out.println(status.getTotalNumber());
		 //   System.out.println(status.getHasvisible());
		} catch (WeiboException e) {
			e.printStackTrace();
		}
	}

}
