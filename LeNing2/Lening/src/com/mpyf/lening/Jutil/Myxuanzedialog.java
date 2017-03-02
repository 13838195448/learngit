package com.mpyf.lening.Jutil;


import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.Window;
import android.view.WindowManager;

import com.mpyf.lening.R;

/**
 * 页面加载显示Dialog
 * @author Administrator
 *
 */
public class Myxuanzedialog {

	public static Dialog MyDialogShow(Context context,int layout,Float liagndu) {
		LayoutInflater factory = LayoutInflater.from(context);
		// 获取自定义布局文件中的控件
		Dialog dialog = new Dialog(context);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(layout);
		
		Window window = dialog.getWindow(); 
		
	    WindowManager.LayoutParams lp = window.getAttributes(); 
	    
	    // 设置透明度为0.5 
	    lp.alpha = liagndu; 
	    window.setAttributes(lp); 
	    window.setBackgroundDrawableResource(R.color.touming);
	    
	    return dialog;
	}
}
