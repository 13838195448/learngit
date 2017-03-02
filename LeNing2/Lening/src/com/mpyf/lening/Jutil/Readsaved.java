package com.mpyf.lening.Jutil;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class Readsaved {

	public static String read(Context context,String key){
		SharedPreferences sharedPreferences = context.getSharedPreferences("saved", Activity.MODE_PRIVATE);    
    	//ʹ��getXxx�������value
    	String value=sharedPreferences.getString(key,"");
    	
    	return value;
	}  
	
	public static int readInt(Context context,String key){
		SharedPreferences sharedPreferences = context.getSharedPreferences("saved", Activity.MODE_PRIVATE);    
    	//ʹ��getXxx�������value
    	int value=sharedPreferences.getInt(key, 0);
    	
    	return value;
	}  
}
