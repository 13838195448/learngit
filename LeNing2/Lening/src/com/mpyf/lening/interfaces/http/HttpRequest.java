package com.mpyf.lening.interfaces.http;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.webkit.CookieManager;

public class HttpRequest extends AsyncTask<String, Integer, DealResult> {

	String requestUrl;
	String requestData;
	Boolean isPost = false;
	RequestCallBack callBack;
	Boolean isImageRequest = false;
	private String sessionId = HttpUse.sessionId;
	@Override
	protected DealResult doInBackground(String... params) {
		String result = "";
		DealResult dealResult = new DealResult();
		try {
			if (isImageRequest) {
				Bitmap map = _getImage(requestUrl);
				dealResult.setResult(true);
				dealResult.setData(map);
			} else {
				if (isPost) {
					result = _sendPost(requestUrl, requestData);
				} else {
					result = _sendGet(requestUrl);
				}
				//判断url是否是登录，如果是，去掉cookie，不是的话加上cookie
				JSONObject dataJson = new JSONObject(result);
				Boolean result1 = dataJson.getBoolean("result");
				dealResult.setResult(result1);
				String message = dataJson.getString("message");
				dealResult.setMessage(message);
				String data = dataJson.getString("data");
				dealResult.setData(data);
			}
		} catch (Exception ero) {
			dealResult.setResult(false);
			Log.i("错误信息", requestData);
			dealResult.setMessage("发送数据时发生错误:" + ero.getMessage());
		}
		return dealResult;
	}

	@Override
    protected void onPostExecute(DealResult result) {
    
//    	Type currentType=callBack.currentType;
//    	Object data=null;
//    	try{
//    	if(currentType.getClass().isArray())
//    	{
//    		currentType = ((ParameterizedType)currentType).getActualTypeArguments()[0];
//    				
//    		data = result.deserializeListData(currentType.getClass());
//    	
//    	}
//    	else
//    	{
//    		data = result.deserializeData(currentType.getClass());
//    	}
//    	}
//    	catch(Exception ero){
//    		result.setResult(false);
//    		result.setMessage("反序列化时发生错误:"+ero.getMessage());
//    	}
    	  
    	//callBack.onCall(result.getResult(),result.getMessage(),data);
		callBack.onCall(result);
    }


	Map<String, String> propertySetting = new HashMap<String, String>();
	Charset charset;
	URLConnection connection;

	public HttpRequest() {
		charset = Charset.defaultCharset();
	}

	public HttpRequest(Charset _charset) {
		charset = _charset;
	}

	public void setRequestProperty(String name, String value) {
		// conn.setRequestProperty(name,value);
		propertySetting.put(name, value);
	}

	public void setCookie(String cookie) {
		propertySetting.put("Cookie", cookie);
	}

	public void sendGet(String url, RequestCallBack _callBack) {
		callBack = _callBack;
		requestUrl = url;
	}

	public void sendPost(String url, String data, RequestCallBack _callBack) {
		callBack = _callBack;
		requestUrl = url;
		requestData = data;
		isPost = true;
	}

	public void getImage(String url, RequestCallBack _callBack) {
		callBack = _callBack;
		requestUrl = url;
		isImageRequest = true;
	}

	String _sendGet(String url) throws Exception {
		String result = "";
		URLConnection connection = getConnection(url);
		connection.setRequestProperty("Cookie",  HttpUse.sessionId);
		connection.connect();
		result = getResponse(connection);
		return result;
	}

	String _sendPost(String url, String data) throws Exception {
		String result = "";
		connection = getConnection(url);
		CookieManager manager = CookieManager.getInstance();
		connection.setRequestProperty("Content-Type", "application/json");
		connection.setRequestProperty("Charset", "utf-8");
		manager.setAcceptCookie(true);
		if(sessionId!=null&&!sessionId.equals("")){
			//connection.setRequestProperty("Cookie", sessionId);
			//connection.setRequestProperty("Set-Cookie", sessionId);
			connection.setRequestProperty("Cookie", HttpUse.sessionId);
			connection.setRequestProperty("Set-Cookie", sessionId);
		}
		
		connection.setDoOutput(true);
		connection.setDoInput(true);
		connection.setUseCaches(false);
		connection.connect();
		
		PrintWriter out = new PrintWriter(connection.getOutputStream());

		out.print(data);

		out.flush();
		out.close();
		
		String cookieVal = connection.getHeaderField("Set-Cookie");
		;
		
		if(cookieVal != null)
		{
			sessionId = cookieVal.substring(0, cookieVal.indexOf(";"));
		}

		result = getResponse(connection);
		return result;
	}

	Bitmap _getImage(String url) throws Exception {
		String result = "";
		URLConnection connection = getConnection(url);

		connection.connect();
		InputStream is = connection.getInputStream();
		Bitmap bitmap = BitmapFactory.decodeStream(is);
		is.close();
		return bitmap;
	}

	URLConnection getConnection(String url) throws Exception {
		URL realUrl = new URL(url);

		connection = realUrl.openConnection();
		for (Map.Entry<String, String> entry : propertySetting.entrySet()) {
			String key = entry.getKey().toString();
			String value = entry.getValue().toString();
			connection.setRequestProperty(key, value);
		}
		return connection;
	}

	String getResponse(URLConnection connection) throws Exception {
		String result = "";
		BufferedReader in = new BufferedReader(new InputStreamReader(
				connection.getInputStream(), charset));
		String line;
		while ((line = in.readLine()) != null) {
			result += line;
		}
		in.close();
		return result;
	}

	/**
	 * 获取cookie的方法
	 * @return
	 */
	public Map<String, String> getCookies() {
		Map<String, String> cookies = new HashMap<String, String>();

		List<String> cookieList = connection.getHeaderFields()
				.get("Set-Cookie");
		if (cookieList != null) {
			for (String cookie : cookieList) {
				int p = cookie.indexOf("=");
				String key = cookie.substring(0, p);
				String value = cookie.substring(p + 1, cookie.indexOf(";"));
				cookies.put(key, value);
			}
		}
		return cookies;
	}
}
