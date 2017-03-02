package com.mpyf.lening.interfaces.bean.Parame;

import com.fasterxml.jackson.annotation.JsonProperty;
/**
*参数基类
*/
public class ParameBase {

/**
*时间 格式 yyyy-MM-dd hh:mm:ss
*/
@JsonProperty("time")
    private String time;
    
/**
*来源网站 = 1, IOS = 2,Android = 3, 微信 = 4
*/
@JsonProperty("sourcefrom")
    private String sourcefrom;
   

/**
*签名
*/
@JsonProperty("token")
    private String token;


public String getTime() {
	return time;
}


public void setTime(String time) {
	this.time = time;
}


public String getSourcefrom() {
	return sourcefrom;
}


public void setSourcefrom(String sourcefrom) {
	this.sourcefrom = sourcefrom;
}


public String getToken() {
	return token;
}


public void setToken(String token) {
	this.token = token;
}




            }



//end of ParameBase


