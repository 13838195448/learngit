package com.mpyf.lening.interfaces.bean.Parame;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TrainComments extends ParameBase {

	@JsonProperty("PK_Com")
	private String PK_Com;
	
	@JsonProperty("PK_Act")
	private String PK_Act;
	
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
	
	@JsonProperty("teacher_LEVEL")
	private int teacher_LEVEL;
	
	@JsonProperty("teacher_CONTENT")
	private String teacher_CONTENT;

	public String getPK_Com() {
		return PK_Com;
	}

	public void setPK_Com(String pK_Com) {
		PK_Com = pK_Com;
	}

	public String getPK_Act() {
		return PK_Act;
	}

	public void setPK_Act(String pK_Act) {
		PK_Act = pK_Act;
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

	public String getCommTime() {
		return commTime;
	}

	public void setCommTime(String commTime) {
		this.commTime = commTime;
	}

	public int getTeacher_LEVEL() {
		return teacher_LEVEL;
	}

	public void setTeacher_LEVEL(int teacher_LEVEL) {
		this.teacher_LEVEL = teacher_LEVEL;
	}

	public String getTeacher_CONTENT() {
		return teacher_CONTENT;
	}

	public void setTeacher_CONTENT(String teacher_CONTENT) {
		this.teacher_CONTENT = teacher_CONTENT;
	}

	
}
