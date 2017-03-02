package com.mpyf.lening.Jutil;

import java.io.File;

import android.app.Application;
import android.os.Environment;

public class MyApp extends Application{

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		File f = new File(Environment.getExternalStorageDirectory()+"/imgCache/");
		if(!f.exists()){
			f.mkdir();
		}
	}

}
