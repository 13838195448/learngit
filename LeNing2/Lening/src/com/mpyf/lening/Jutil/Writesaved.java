package com.mpyf.lening.Jutil;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class Writesaved {
	

	public static  void write(Context context,String key,String value){
		//（第1步）获得SharedPreferences对象    （文件名称，文件的访问权限：MODE_WORLD_READABLE-读；MODE_WORLD_WRITEABLE-写；MODE_PRIVATE、MODE_APPEND-其他用户不可访问）
    	SharedPreferences sharedPreferences = context.getSharedPreferences("saved", Activity.MODE_PRIVATE);    
    	//（第2步）获得SharedPreferences.Editor对象    
    	SharedPreferences.Editor editor = sharedPreferences.edit();   
    	//（第3步）保存组件中的值    
    	editor.putString(key,value);
    	
    	editor.commit();
	}
	
	
	public static  void writeInt(Context context,String key,int value){
		//（第1步）获得SharedPreferences对象    （文件名称，文件的访问权限：MODE_WORLD_READABLE-读；MODE_WORLD_WRITEABLE-写；MODE_PRIVATE、MODE_APPEND-其他用户不可访问）
    	SharedPreferences sharedPreferences = context.getSharedPreferences("saved", Activity.MODE_PRIVATE);    
    	//（第2步）获得SharedPreferences.Editor对象    
    	SharedPreferences.Editor editor = sharedPreferences.edit();   
    	//（第3步）保存组件中的值    
    	editor.putInt(key,value);
    	
    	editor.commit();
	}
}
