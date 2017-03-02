package com.mpyf.lening.interfaces.http;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import android.util.Log;
import android.webkit.CookieManager;

import com.fasterxml.jackson.databind.JsonSerializer;
import com.mpyf.lening.interfaces.bean.Parame.ParameBase;

public class HttpUse {

	public static String sessionId = "";

	/**
	 * get
	 * 
	 * @param path接口
	 * @param method方法
	 * @param map数据
	 * @return
	 */
	public static String messageget(String path, String method,
			Map<String, Object> map) {
		String result = "";
		try {
			String parme = formatParame(map);

			URL restServiceURL = new URL(Setting.apiUrl + path + "!" + method
					+ ".action?" + parme);

			HttpURLConnection httpConnection = (HttpURLConnection) restServiceURL
					.openConnection();
			httpConnection.setRequestMethod("GET");
			httpConnection.setRequestProperty("Cookie", sessionId);
			httpConnection.setConnectTimeout(10000);
			httpConnection.connect();
			int code = httpConnection.getResponseCode();
			System.out.println(code + "======ssssss");
			if (httpConnection.getResponseCode() != 200) {
				throw new RuntimeException(
						"HTTP GET Request Failed with Error code : "
								+ httpConnection.getResponseCode());
			}
			BufferedReader responseBuffer = new BufferedReader(
					new InputStreamReader((httpConnection.getInputStream())));
			String output;
			while ((output = responseBuffer.readLine()) != null) {
				result += output;
			}
			httpConnection.disconnect();

		} catch (Exception e) {
			e.getMessage();
		}
		return result;

	}

	/**
	 * post
	 * 
	 * @param path
	 *            接口
	 * @param method
	 *            方法
	 * @param parameBase
	 *            对象
	 * @return
	 */
	public static String messagepost(String path, String method,
			ParameBase parameBase) {
		String result = "";
		try {
			String url = Setting.apiUrl + path + "!" + method + ".action";
			URL restServiceURL = new URL(url);
			CookieManager manager = CookieManager.getInstance();
			manager.setAcceptCookie(true);
			HttpURLConnection conn = (HttpURLConnection) restServiceURL
					.openConnection();
			conn.setRequestMethod("POST");
			conn.setDoInput(true);
			conn.setDoOutput(true);
			// conn.setConnectTimeout(10000);
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Charset", "utf-8");
			if (!method.equals("login") && sessionId != null
					&& !sessionId.equals("")) {
				conn.setRequestProperty("Cookie", sessionId);
				conn.setRequestProperty("Set-Cookie", sessionId);
			}

			conn.connect();

			DataOutputStream dataout = new DataOutputStream(
					conn.getOutputStream());
			// 获取输出流
			String parame = formatParame(parameBase);
			if ("saveVote".equals(method)) {
				System.out.println("=拼接请求Json=" + parame);

			}
			dataout.write(parame.getBytes());
			// dataout.writeBytes(parame);

			dataout.flush();
			dataout.close();

			int code = conn.getResponseCode();
			// Log.i("code", code+"");
			String cookieVal = conn.getHeaderField("Set-Cookie");
			if (cookieVal != null) {
				sessionId = cookieVal.substring(0, cookieVal.indexOf(";"));
			}

			// 获取响应的输入流对象
			BufferedReader br = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));
			StringBuffer sb = new StringBuffer();
			String line;
			while ((line = br.readLine()) != null) {
				line = new String(line.getBytes(), "utf-8");
				sb.append(line);
			}
			result = sb.toString();

			br.close();
			conn.disconnect();

		} catch (Exception e) {
			return e.getMessage();
		}

		return result;

	}

	/**
	 * post
	 * 
	 * @param path
	 *            接口
	 * @param method
	 *            方法
	 * @param Map
	 *            对象
	 * @return
	 */
	public static String messagepost(String path, String method,
			Map<String, Object> parameBase) {
		String result = "";
		try {
			String url = Setting.apiUrl + path + "!" + method + ".action";
			URL restServiceURL = new URL(url);
			CookieManager manager = CookieManager.getInstance();
			manager.setAcceptCookie(true);
			HttpURLConnection conn = (HttpURLConnection) restServiceURL
					.openConnection();
			conn.setRequestMethod("POST");
			conn.setDoInput(true);
			conn.setDoOutput(true);
			// conn.setConnectTimeout(10000);
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Charset", "utf-8");
			if (!method.equals("login") && sessionId != null
					&& !sessionId.equals("")) {
				conn.setRequestProperty("Cookie", sessionId);
				conn.setRequestProperty("Set-Cookie", sessionId);
			}

			conn.connect();

			DataOutputStream dataout = new DataOutputStream(
					conn.getOutputStream());
			// 获取输出流
			String parame = formatParame(parameBase);
			if ("saveVote".equals(method)) {
				System.out.println("=拼接请求Json=" + parame);

			}

			dataout.write(parame.getBytes());
			// dataout.writeBytes(parame);

			dataout.flush();
			dataout.close();

			int code = conn.getResponseCode();
			// Log.i("code", code+"");
			String cookieVal = conn.getHeaderField("Set-Cookie");
			if (cookieVal != null) {
				sessionId = cookieVal.substring(0, cookieVal.indexOf(";"));
			}

			// 获取响应的输入流对象
			BufferedReader br = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));
			StringBuffer sb = new StringBuffer();
			String line;
			while ((line = br.readLine()) != null) {
				line = new String(line.getBytes(), "utf-8");
				sb.append(line);
			}
			result = sb.toString();
			br.close();
			conn.disconnect();

		} catch (Exception e) {
			return e.getMessage();
		}

		return result;

	}

	private static String formatParame(Map<String, Object> dic)
			throws Exception {
		Date date = new Date();
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String time = format.format(date);
		dic.put("time", time);
		dic.put("sourcefrom", 3);

		String parame = "";
		String str = "";

		Collection<String> keyset = dic.keySet();
		List<String> list = new ArrayList<String>(keyset);

		Collections.sort(list, new Comparator<String>() {

			public int compare(String arg0, String arg1) {
				return arg0.toLowerCase().compareTo(arg1.toLowerCase());
			}
		});

		for (int i = 0; i < list.size(); i++) {
			String key = list.get(i);
			Object value = dic.get(key).toString();
			;
			if (value == null) {
				value = "";
			}
			parame += String.format("%s=%s&", key,
					URLEncoder.encode(value.toString(), "utf-8"));
			str += value + "";
		}
		String token = MD5.getMD5(str + Setting.apiKey);
		parame += String.format("%s=%s", "token", token);
		return parame;
	}

	private static String formatParame(ParameBase msg) throws Exception {
		msg.setSourcefrom("3");
		Date date = new Date();
		DateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String time = format.format(date);
		msg.setTime(time);
		Class _class = msg.getClass();
		Field[] fields = _class.getDeclaredFields();

		if (_class.getGenericSuperclass() != null) {
			Class superClass = _class.getSuperclass();
			Field[] fieldsSuper = superClass.getDeclaredFields();
			fields = concat(fields, fieldsSuper);
		}
		List<Field> fields2 = Arrays.asList(fields);
		Collections.sort(fields2, new Comparator<Field>() {
			public int compare(Field arg0, Field arg1) {

				return arg0.getName().toLowerCase()
						.compareTo(arg1.getName().toLowerCase());
			}
		});
		String str = "";

		for (Field f : fields2) {
			String name = f.getName();
			// Log.i("name", name);
			if (name.toLowerCase() == "token") {
				continue;
			}
			Class classCurrent = f.getClass();

			if (classCurrent.isArray()) {
				continue;
			}
			String typeName = f.getType().getName();

			// Log.i("typeName", typeName.toString());
			if (typeName.contains("java.util.List")) {
				continue;
			}
			if (typeName.contains("[")) {
				continue;
			}
			String metodName = "get" + toUpperCaseFirstOne(name);
			Method m = msg.getClass().getMethod(metodName);
			Object value = m.invoke(msg);
			if (value == null) {
				value = "";
			}
			str += value + "";
		}
		// 序列化后向接口提交的字符串
		String token = MD5.getMD5(str + Setting.apiKey);
		msg.setToken(token);
		return JsonUtils.serialize(msg);
	}

	public static String toUpperCaseFirstOne(String s) {
		if (Character.isUpperCase(s.charAt(0)))
			return s;
		else
			return (new StringBuilder())
					.append(Character.toUpperCase(s.charAt(0)))
					.append(s.substring(1)).toString();
	}

	static Field[] concat(Field[] a, Field[] b) {
		Field[] c = new Field[a.length + b.length];
		System.arraycopy(a, 0, c, 0, a.length);
		System.arraycopy(b, 0, c, a.length, b.length);
		return c;
	}

	/**
	 * 获取cookie的方法
	 * 
	 * @return
	 */
	public static Map<String, String> cookie(HttpURLConnection connection) {
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
