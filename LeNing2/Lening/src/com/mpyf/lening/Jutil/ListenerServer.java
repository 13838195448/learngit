package com.mpyf.lening.Jutil;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;

public class ListenerServer {

	/**
	 * ·µ»Ø
	 */
	public static void setfinish(final Activity activity, View view) {
		view.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				activity.finish();
			}
		});
	}

	/**
	 * Ìø×ª
	 */
//	public static void startto(View view, Activity activity, Class<> activity2) {
//		view.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View arg0) {
//				// TODO Auto-generated method stub
//				activity.startActivity(new Intent(activity, activity2
//						.getClass()));
//			}
//		});
//
//	}

}
