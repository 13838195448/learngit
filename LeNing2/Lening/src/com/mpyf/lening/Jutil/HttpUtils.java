package com.mpyf.lening.Jutil;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/** * * 网络获取类 * * @author wanghao * */
public class HttpUtils {
	/** * * 根据URL获取输入流对象 * * @param imageURL 文件URL地址 * * @return 输入流对象 * */
	public static InputStream getStreamFromURL(String imageURL) {
		InputStream in = null;
		try {
			URL url = new URL(imageURL);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			in = connection.getInputStream();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return in;
	}
}