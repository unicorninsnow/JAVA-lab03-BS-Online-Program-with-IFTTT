package background;
/**根据微博账号和密码来获取access_token
 * @author  daniel
 * created on 2013-12-1
 */
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import weibo4j.Oauth;
import weibo4j.Timeline;
import weibo4j.http.AccessToken;
import weibo4j.model.WeiboException;
import weibo4j.util.WeiboConfig;

/***
 * 妯℃嫙鑷姩鐧诲綍骞跺彂寰崥
 * @author Daniel
 * 
 */
public class getWeiboToken{
	/***
	 * 妯℃嫙鐧诲綍骞跺緱鍒扮櫥褰曞悗鐨凾oken
	 * @param username 鐢ㄦ埛鍚�
	 * @param password 瀵嗙爜
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
	public static String getToken(String username, String password) throws HttpException, IOException {
		String clientId = WeiboConfig.getValue("client_ID");
		String redirectURI = WeiboConfig.getValue("redirect_URI");
		String url = WeiboConfig.getValue("authorizeURL");

		PostMethod postMethod = new PostMethod(url);
		// 搴旂敤鐨凙pp Key
		postMethod.addParameter("client_id", clientId);
		// 搴旂敤鐨勯噸瀹氬悜椤甸潰
		postMethod.addParameter("redirect_uri", redirectURI);
		// 妯℃嫙鐧诲綍鍙傛暟
		// 寮�彂鑰呮垨娴嬭瘯璐﹀彿鐨勭敤鎴峰悕鍜屽瘑鐮�
		postMethod.addParameter("userId", username);
		postMethod.addParameter("isLoginSina", "0");
		postMethod.addParameter("action", "submit");
		postMethod.addParameter("response_type", "code");
		HttpMethodParams param = postMethod.getParams();
		param.setContentCharset("UTF-8");
		// 娣诲姞澶翠俊鎭�
		List<Header> headers = new ArrayList<Header>();
		headers.add(new Header("Referer", "https://api.weibo.com/oauth2/authorize?client_id=" + clientId
				+ "&redirect_uri=" + redirectURI + "&from=sina&response_type=code"));
		headers.add(new Header("Host", "api.weibo.com"));
		headers.add(new Header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; rv:11.0) Gecko/20100101 Firefox/11.0"));
		HttpClient client = new HttpClient();
		client.getHostConfiguration().getParams().setParameter("http.default-headers", headers);
		client.executeMethod(postMethod);
		int status = postMethod.getStatusCode();
		if (status != 302) {
			System.out.println("token鍒锋柊澶辫触");
			return null;
		}
		// 瑙ｆ瀽Token
		Header location = postMethod.getResponseHeader("Location");
		if (location != null) {
			String retUrl = location.getValue();
			int begin = retUrl.indexOf("code=");
			if (begin != -1) {
				int end = retUrl.indexOf("&", begin);
				if (end == -1)
					end = retUrl.length();
				String code = retUrl.substring(begin + 5, end);
				if (code != null) {
					Oauth oauth = new Oauth();
					try {
						AccessToken token = oauth.getAccessTokenByCode(code);
						return token.getAccessToken();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		return null;
	}
	
}
