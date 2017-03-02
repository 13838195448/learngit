package com.mpyf.lening.interfaces.http;

import java.lang.reflect.Type;







public interface  RequestCallBack {
//	public Class currentType;
//
//	public RequestCallBack(Class type) {
//		currentType=type;
//		
//	}
//
//	
//
//	public abstract void onCall(Boolean result, String error, Object data);
//	
	 public void onCall(DealResult result);
}

