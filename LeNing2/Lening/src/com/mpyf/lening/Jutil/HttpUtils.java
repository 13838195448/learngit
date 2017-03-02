package com.mpyf.lening.Jutil;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/** * * �����ȡ�� * * @author wanghao * */
public class HttpUtils {
	/** * * ����URL��ȡ���������� * * @param imageURL �ļ�URL��ַ * * @return ���������� * */
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