package com.mpyf.lening.interfaces.bean.Parame;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Administrator
 *×ÊÔ´·ÖÏí
 */
public class ShareComment extends ParameBase{
	
	@JsonProperty("PK_Com")
	public String PK_Com;
	@JsonProperty("PK_Share")
	public String PK_Share;
	public String getPK_Com() {
		return PK_Com;
	}
	public void setPK_Com(String pK_Com) {
		PK_Com = pK_Com;
	}
	public String getPK_Share() {
		return PK_Share;
	}
	public void setPK_Share(String pK_Share) {
		PK_Share = pK_Share;
	}
	public String getCom_Con() {
		return com_Con;
	}
	public void setCom_Con(String com_Con) {
		this.com_Con = com_Con;
	}
	public int getCom_Level() {
		return com_Level;
	}
	public void setCom_Level(int com_Level) {
		this.com_Level = com_Level;
	}
	public int getPk_users() {
		return pk_users;
	}
	public void setPk_users(int pk_users) {
		this.pk_users = pk_users;
	}
	public String getTrueName() {
		return trueName;
	}
	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getCom_Date() {
		return com_Date;
	}
	public void setCom_Date(String com_Date) {
		this.com_Date = com_Date;
	}
	@JsonProperty("com_Con")
	public String com_Con;
	@JsonProperty("com_Level")
	public int com_Level;
	@JsonProperty("pk_users")
	public int pk_users;
	@JsonProperty("trueName")
	public String trueName;
	@JsonProperty("userName")
	public String userName;
	@JsonProperty("com_Date")
	public String com_Date;
}
