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
		.showImageOnLoading(R.drawable.defaultimage) // 设置图片下载期间显示的图片
		.showImageOnFail(R.drawable.defaultimage)
		// 设置图片加载或解码过程中发生错误显示的图片
		.showImageForEmptyUri(R.drawable.defaultimage)
		.cacheInMemory(true) // 设置下载的图片是否缓存在内存中
		.cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中
		//.displayer(new RoundDisplayer()) 
	//	.displayer(new RoundedBitmapDisplayer(30)) // 设置成圆角图片
		 .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)// 设置图片以如何的编码方式显示
        .bitmapConfig(Bitmap.Config.ALPHA_8)// 设置图片的解码类型//
		.build(); // 构建完成
			

	}
	
	/**
	 * 把图片URL转换成Bitmap
	 */
	public static Bitmap getBitmap(String url) {  
        Bitmap bm = null;  
        try {  
            URL iconUrl = new URL(url);  
            URLConnection conn = iconUrl.openConnection();  
            HttpURLConnection http = (HttpURLConnection) conn;  
              
            int length = http.getContentLength();  
              
            conn.connect();  
            // 获得图像的字符流  
            InputStream is = conn.getInputStream();  
            BufferedInputStream bis = new BufferedInputStream(is, length);  
            bm = BitmapFactory.decodeStream(bis);  
            bis.close();  
            is.close();// 关闭流  
        }  
        catch (Exception e) {  
            e.printStackTrace();  
        }  
        return bm;  
    }
		
	
}
