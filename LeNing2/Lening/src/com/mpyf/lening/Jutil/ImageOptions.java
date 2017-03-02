package com.mpyf.lening.Jutil;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import com.mpyf.lening.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.DisplayImageOptions.Builder;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
public  class  ImageOptions {
 
	public static DisplayImageOptions options;
	//public static DisplayImageOptions options2;
	public static void setOption(){
			options = new DisplayImageOptions.Builder()
		.showImageOnLoading(R.drawable.defaultimage) // ����ͼƬ�����ڼ���ʾ��ͼƬ
		.showImageOnFail(R.drawable.defaultimage)
		// ����ͼƬ���ػ��������з���������ʾ��ͼƬ
		.showImageForEmptyUri(R.drawable.defaultimage)
		.cacheInMemory(true) // �������ص�ͼƬ�Ƿ񻺴����ڴ���
		.cacheOnDisk(true) // �������ص�ͼƬ�Ƿ񻺴���SD����
		//.displayer(new RoundDisplayer()) 
	//	.displayer(new RoundedBitmapDisplayer(30)) // ���ó�Բ��ͼƬ
		 .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)// ����ͼƬ����εı��뷽ʽ��ʾ
        .bitmapConfig(Bitmap.Config.ALPHA_8)// ����ͼƬ�Ľ�������//
		.build(); // �������
			

	}
	
	/**
	 * ��ͼƬURLת����Bitmap
	 */
	public static Bitmap getBitmap(String url) {  
        Bitmap bm = null;  
        try {  
            URL iconUrl = new URL(url);  
            URLConnection conn = iconUrl.openConnection();  
            HttpURLConnection http = (HttpURLConnection) conn;  
              
            int length = http.getContentLength();  
              
            conn.connect();  
            // ���ͼ����ַ���  
            InputStream is = conn.getInputStream();  
            BufferedInputStream bis = new BufferedInputStream(is, length);  
            bm = BitmapFactory.decodeStream(bis);  
            bis.close();  
            is.close();// �ر���  
        }  
        catch (Exception e) {  
            e.printStackTrace();  
        }  
        return bm;  
    }
		
	
}
