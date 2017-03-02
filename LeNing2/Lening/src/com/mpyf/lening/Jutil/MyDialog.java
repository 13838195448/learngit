package com.mpyf.lening.Jutil;


import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Window;
import android.view.WindowManager;

import com.mpyf.lening.R;

/**
 * 页面加载显示Dialog
 * @author Administrator
 *
 */
public class MyDialog {

	public static Dialog MyDialogShow(Context context,int layout,Float liagndu) {
		LayoutInflater factory = LayoutInflater.from(context);
		// 获取自定义布局文件中的控件
		Dialog dialog = new Dialog(context);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(layout);
		dialog.setCanceledOnTouchOutside(true);
		Window window = dialog.getWindow(); 
		
	    WindowManager.LayoutParams lp = window.getAttributes(); 
	    
	    // 设置透明度为0.5 
	    lp.alpha = liagndu; 
	    window.setAttributes(lp); 
	    window.setBackgroundDrawableResource(R.color.touming);
	    window.setWindowAnimations(R.style.qiandao);
	    
	    return dialog;
	}
	
	public static Dialog MyDialogxuanxiang(Context context,int layout,Float liagndu,int x,int y) {
		LayoutInflater factory = LayoutInflater.from(context);
		// 获取自定义布局文件中的控件
		Dialog dialog = new Dialog(context);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(layout);
		
		dialog.setCanceledOnTouchOutside(true);
		Window window = dialog.getWindow();
		window.setGravity(Gravity.LEFT|Gravity.TOP);
	    WindowManager.LayoutParams lp = window.getAttributes(); 
	    
	    // 设置透明度为0.5 
	    lp.alpha = liagndu; 
	    lp.x=x;
	    lp.y=y;
	    window.setAttributes(lp); 
	    window.setBackgroundDrawableResource(R.color.touming);
//	    window.setWindowAnimations(R.style.xuanxiang);
	    
	    return dialog;
	}
	
	public static Dialog MyDialogloading(Context context) {
		LayoutInflater factory = LayoutInflater.from(context);
		// 获取自定义布局文件中的控件
		Dialog dialog = new Dialog(context);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.popupwindow_loading);
		
		dialog.setCanceledOnTouchOutside(true);
		Window window = dialog.getWindow();
		window.setGravity(Gravity.CENTER);
	    WindowManager.LayoutParams lp = window.getAttributes(); 
	    
	    
//	    lp.alpha = liagndu; 
	    window.setAttributes(lp); 
	    window.setBackgroundDrawableResource(R.color.touming);
	    
	    return dialog;
	}
	public static Dialog LoginSplash(Context context) {
		LayoutInflater factory = LayoutInflater.from(context);
		// 获取自定义布局文件中的控件
		Dialog dialog = new Dialog(context);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.dialog_splash);
		
		dialog.setCanceledOnTouchOutside(true);
		Window window = dialog.getWindow();
		window.setGravity(Gravity.CENTER);
	    WindowManager.LayoutParams lp = window.getAttributes(); 
	    
	    
//	    lp.alpha = liagndu; 
	    window.setAttributes(lp); 
	    window.setBackgroundDrawableResource(R.color.touming);
	    
	    return dialog;
	}
	
	
	public static Dialog MyDialogShow(Context context,int layout,Float liagndu,float y) {
		LayoutInflater factory = LayoutInflater.from(context);
		// 获取自定义布局文件中的控件
		Dialog dialog = new Dialog(context);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(layout);
		dialog.setCanceledOnTouchOutside(true);
		Window window = dialog.getWindow(); 
		
		 WindowManager.LayoutParams lp = window.getAttributes(); 
	   
		 
	    lp.y = (int)y;
	    // 设置透明度为0.5 
	    lp.alpha = liagndu; 
	    lp.width=window.getWindowManager().getDefaultDisplay().getWidth();
	    window.setAttributes(lp); 
//	    window.setBackgroundDrawableResource(R.color.touming);
	    
	    return dialog;
	}
}
