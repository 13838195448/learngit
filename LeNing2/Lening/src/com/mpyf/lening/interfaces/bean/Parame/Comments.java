package com.mpyf.lening.interfaces.bean.Parame;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Comments extends ParameBase{

	@JsonProperty("PK_Com")
	private String PK_Com;
	
	@JsonProperty("PK_Course")
	private String PK_Course;
	
	@JsonProperty("PK_Res")
	private String PK_Res;
	
	@JsonProperty("pk_user")
	private int pk_user;
	
	@JsonProperty("userName")
	private String userName;
	
	@JsonProperty("nickname")
	private String nickname;
	
	@JsonProperty("COMMENT_LEVEL")
	private int COMMENT_LEVEL;
	
	@JsonProperty("COMMENT_CONTENT")
	private String COMMENT_CONTENT;
	
	@JsonProperty("commTime")
	private String commTime;

	@JsonProperty("resName")
	private String resName;

	public String getPK_Com() {
		return PK_Com;
	}

	public void setPK_Com(String pK_Com) {
		PK_Com = pK_Com;
	}

	public String getPK_Course() {
		return PK_Course;
	}

	public void setPK_Course(String pK_Course) {
		PK_Course = pK_Course;
	}

	public String getPK_Res() {
		return PK_Res;
	}

	public void setPK_Res(String pK_Res) {
		PK_Res = pK_Res;
	}

	public int getPk_user() {
		return pk_user;
	}

	public void setPk_user(int pk_user) {
		this.pk_user = pk_user;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public int getCOMMENT_LEVEL() {
		return COMMENT_LEVEL;
	}

	public void setCOMMENT_LEVEL(int cOMMENT_LEVEL) {
		COMMENT_LEVEL = cOMMENT_LEVEL;
	}

	public String getCOMMENT_CONTENT() {
		return COMMENT_CONTENT;
	}

	public void setCOMMENT_CONTENT(String cOMMENT_CONTENT) {
		COMMENT_CONTENT = cOMMENT_CONTENT;
	}


	public String getResName() {
		return resName;
	}

	public void setResName(String resName) {
		this.resName = resName;
	}

	public String getCommTime() {
		return commTime;
	}

	public void setCommTime(String commTime) {
		this.commTime = commTime;
	}
	
	
	
	
}
