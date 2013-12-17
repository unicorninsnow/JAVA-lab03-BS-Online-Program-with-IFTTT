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
 * 模拟登录并得到登录后的Token
 * @author Daniel
 * 
 */
public class getWeiboToken{
	/***
	 * 模拟登录并得到登录后的Token
	 * @param username 用户名
	 * @param password 密码
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
	public static String getToken(String username, String password) throws HttpException, IOException {
		String clientId = WeiboConfig.getValue("client_ID");
		String redirectURI = WeiboConfig.getValue("redirect_URI");
		String url = WeiboConfig.getValue("authorizeURL");

		PostMethod postMethod = new PostMethod(url);
		// 应用的App Key
		postMethod.addParameter("client_id", clientId);
		// 应用的重定向页面
		postMethod.addParameter("redirect_uri", redirectURI);
		// 模拟登录参数
		// 开发者或测试账号的用户名和密码
		postMethod.addParameter("userId", username);
		postMethod.addParameter("passwd", password);
		postMethod.addParameter("isLoginSina", "0");
		postMethod.addParameter("action", "submit");
		postMethod.addParameter("response_type", "code");
		HttpMethodParams param = postMethod.getParams();
		param.setContentCharset("UTF-8");
		// 添加头信息
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
			System.out.println("token刷新失败");
			return null;
		}
		// 解析Token
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
