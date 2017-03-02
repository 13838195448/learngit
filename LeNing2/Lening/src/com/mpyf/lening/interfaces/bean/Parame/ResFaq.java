package com.mpyf.lening.interfaces.bean.Parame;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResFaq extends ParameBase {

	@JsonProperty("PK_Faq")
	private String PK_Faq;

	@JsonProperty("PK_Course")
	private String PK_Course;

	@JsonProperty("PK_Res")
	private String PK_Res;

	@JsonProperty("userName")
	private String userName;

	@JsonProperty("nickname")
	private String nickname;

	@JsonProperty("FAQ_CONTENT")
	private String FAQ_CONTENT;

	@JsonProperty("FAQ_Time")
	private String FAQ_Time;

	@JsonProperty("ans_CONTENT")
	private String ans_CONTENT;

	@JsonProperty("ans_Time")
	private String ans_Time;

	@JsonProperty("ans_username")
	private String ans_username;

	@JsonProperty("ans_nickname")
	private String ans_nickname;

	@JsonProperty("pk_user")
	private Integer pk_user;

	@JsonProperty("beAnswered")
	private boolean beAnswered;

	@JsonProperty("res_Name")
	private String res_Name;

	public String getPK_Faq() {
		return PK_Faq;
	}

	public void setPK_Faq(String pK_Faq) {
		PK_Faq = pK_Faq;
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

	public String getFAQ_CONTENT() {
		return FAQ_CONTENT;
	}

	public void setFAQ_CONTENT(String fAQ_CONTENT) {
		FAQ_CONTENT = fAQ_CONTENT;
	}

	public String getFAQ_Time() {
		return FAQ_Time;
	}

	public void setFAQ_Time(String fAQ_Time) {
		FAQ_Time = fAQ_Time;
	}

	public String getAns_CONTENT() {
		return ans_CONTENT;
	}

	public void setAns_CONTENT(String ans_CONTENT) {
		this.ans_CONTENT = ans_CONTENT;
	}

	public String getAns_Time() {
		return ans_Time;
	}

	public void setAns_Time(String ans_Time) {
		this.ans_Time = ans_Time;
	}

	public String getAns_username() {
		return ans_username;
	}

	public void setAns_username(String ans_username) {
		this.ans_username = ans_username;
	}

	public String getAns_nickname() {
		return ans_nickname;
	}

	public void setAns_nickname(String ans_nickname) {
		this.ans_nickname = ans_nickname;
	}

	public Integer getPk_user() {
		return pk_user;
	}

	public void setPk_user(Integer pk_user) {
		this.pk_user = pk_user;
	}

	public boolean isBeAnswered() {
		return beAnswered;
	}

	public void setBeAnswered(boolean beAnswered) {
		this.beAnswered = beAnswered;
	}

	public String getRes_Name() {
		return res_Name;
	}

	public void setRes_Name(String res_Name) {
		this.res_Name = res_Name;
	}

	
	
}
