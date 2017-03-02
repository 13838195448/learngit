package com.mpyf.lening.Jutil;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;

public class Xiaoxibeijing {

	public  void changetop(Activity context,int color) {
		context.requestWindowFeature(Window.FEATURE_NO_TITLE);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			setTranslucentStatus(context, true);
		}
		SystemBarTintManager tintManager = new SystemBarTintManager(context);
		tintManager.setStatusBarTintEnabled(true);
		tintManager.setStatusBarTintResource(color);
	}

	@TargetApi(19)
	private  void setTranslucentStatus(Activity context, boolean on) {
		Window win = context.getWindow();
		WindowManager.LayoutParams winParams = win.getAttributes();
		final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
		if (on) {
			winParams.flags |= bits;
		} else {
			winParams.flags &= ~bits;
		}
		win.setAttributes(winParams);
	}

}
