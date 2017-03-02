package com.mpyf.lening.interfaces.bean.Parame;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Praises extends ParameBase{

	@JsonProperty("PK_Praises")
	private String PK_Praises;
	
	@JsonProperty("Pk_user")
	private int Pk_user;
	
	@JsonProperty("UserName")
	private String UserName;
	
	@JsonProperty("Nickname")
	private String Nickname;
	
	@JsonProperty("TrueName")
	private String TrueName;
	
	@JsonProperty("Praise_DATE")
	private String Praise_DATE;
	
	@JsonProperty("reason")
	private String reason;
	
	@JsonProperty("creat_user")
	private int creat_user;
	
	@JsonProperty("creatName")
	private String creatName;

	public String getPK_Praises() {
		return PK_Praises;
	}

	public void setPK_Praises(String pK_Praises) {
		PK_Praises = pK_Praises;
	}

	public int getPk_user() {
		return Pk_user;
	}

	public void setPk_user(int pk_user) {
		Pk_user = pk_user;
	}

	public String getUserName() {
		return UserName;
	}

	public void setUserName(String userName) {
		UserName = userName;
	}

	public String getNickname() {
		return Nickname;
	}

	public void setNickname(String nickname) {
		Nickname = nickname;
	}

	public String getTrueName() {
		return TrueName;
	}

	public void setTrueName(String trueName) {
		TrueName = trueName;
	}

	public String getPraise_DATE() {
		return Praise_DATE;
	}

	public void setPraise_DATE(String praise_DATE) {
		Praise_DATE = praise_DATE;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public int getCreat_user() {
		return creat_user;
	}

	public void setCreat_user(int creat_user) {
		this.creat_user = creat_user;
	}

	public String getCreatName() {
		return creatName;
	}

	public void setCreatName(String creatName) {
		this.creatName = creatName;
	}
	
	
	
}
