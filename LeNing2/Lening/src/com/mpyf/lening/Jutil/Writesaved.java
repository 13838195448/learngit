package com.mpyf.lening.Jutil;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class Writesaved {
	

	public static  void write(Context context,String key,String value){
		//����1�������SharedPreferences����    ���ļ����ƣ��ļ��ķ���Ȩ�ޣ�MODE_WORLD_READABLE-����MODE_WORLD_WRITEABLE-д��MODE_PRIVATE��MODE_APPEND-�����û����ɷ��ʣ�
    	SharedPreferences sharedPreferences = context.getSharedPreferences("saved", Activity.MODE_PRIVATE);    
    	//����2�������SharedPreferences.Editor����    
    	SharedPreferences.Editor editor = sharedPreferences.edit();   
    	//����3������������е�ֵ    
    	editor.putString(key,value);
    	
    	editor.commit();
	}
	
	
	public static  void writeInt(Context context,String key,int value){
		//����1�������SharedPreferences����    ���ļ����ƣ��ļ��ķ���Ȩ�ޣ�MODE_WORLD_READABLE-����MODE_WORLD_WRITEABLE-д��MODE_PRIVATE��MODE_APPEND-�����û����ɷ��ʣ�
    	SharedPreferences sharedPreferences = context.getSharedPreferences("saved", Activity.MODE_PRIVATE);    
    	//����2�������SharedPreferences.Editor����    
    	SharedPreferences.Editor editor = sharedPreferences.edit();   
    	//����3������������е�ֵ    
    	editor.putInt(key,value);
    	
    	editor.commit();
	}
}
