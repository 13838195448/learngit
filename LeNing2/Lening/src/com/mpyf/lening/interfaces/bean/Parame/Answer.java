package com.mpyf.lening.interfaces.bean.Parame;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


public class Answer extends ParameBase{
	@JsonProperty("pic1")
	@JsonInclude(Include.NON_NULL)
	private byte[] pic1;
	@JsonProperty("pic2")
	@JsonInclude(Include.NON_NULL)
	private byte[] pic2;
	@JsonProperty("pic3")
	@JsonInclude(Include.NON_NULL)
	private byte[] pic3;
	@JsonProperty("pic4")
	@JsonInclude(Include.NON_NULL)
	private byte[] pic4;
	@JsonProperty("pic5")
	@JsonInclude(Include.NON_NULL)
	
	private byte[] pic5;
	@JsonProperty("pic6")
	@JsonInclude(Include.NON_NULL)
	
	private byte[] pic6;
	@JsonProperty("pic7")
	@JsonInclude(Include.NON_NULL)
	private byte[] pic7;
	@JsonProperty("pic8")
	@JsonInclude(Include.NON_NULL)
	private byte[] pic8;
	@JsonProperty("pic9")
	@JsonInclude(Include.NON_NULL)
	private byte[] pic9;

	@JsonProperty("pic_num")
	private int pic_num;

	public byte[] getPic1() {
		return pic1;
	}
	public void setPic1(byte[] pic1) {
		this.pic1 = pic1;
	}
	public byte[] getPic2() {
		return pic2;
	}
	public void setPic2(byte[] pic2) {
		this.pic2 = pic2;
	}
	public byte[] getPic3() {
		return pic3;
	}
	public void setPic3(byte[] pic3) {
		this.pic3 = pic3;
	}
	public byte[] getPic4() {
		return pic4;
	}
	public void setPic4(byte[] pic4) {
		this.pic4 = pic4;
	}
	public byte[] getPic5() {
		return pic5;
	}
	public void setPic5(byte[] pic5) {
		this.pic5 = pic5;
	}
	public byte[] getPic6() {
		return pic6;
	}
	public void setPic6(byte[] pic6) {
		this.pic6 = pic6;
	}
	public byte[] getPic7() {
		return pic7;
	}
	public void setPic7(byte[] pic7) {
		this.pic7 = pic7;
	}
	public byte[] getPic8() {
		return pic8;
	}
	public void setPic8(byte[] pic8) {
		this.pic8 = pic8;
	}
	public byte[] getPic9() {
		return pic9;
	}
	public void setPic9(byte[] pic9) {
		this.pic9 = pic9;
	}
	public int getPic_num() {
		return pic_num;
	}
	public void setPic_num(int pic_num) {
		this.pic_num = pic_num;
	}
	@JsonProperty("ANS_CONTENT")
	private String ANS_CONTENT;
	@JsonProperty("ANS_STATE")
	private int ANS_STATE;
	@JsonProperty("BAD_NUM")
	private int BAD_NUM;
	@JsonProperty("GOOD_NUM")
	private int GOOD_NUM;
	@JsonProperty("PK_Ans")
	private String PK_Ans;
	@JsonProperty("PK_Que")
	private String PK_Que;
	@JsonProperty("ansTime")
	private String ansTime;
	@JsonProperty("is_child")
	private int is_child;
	@JsonProperty("is_havechild")
	private int is_havechild;
	@JsonProperty("nickname")
	private String nickname;
	@JsonProperty("p_Nickname")
	private String p_Nickname;
	@JsonProperty("p_Pk_user")
	private int p_Pk_user;
	@JsonProperty("p_TrueName")
	private String p_TrueName;
	@JsonProperty("p_UserName")
	private String p_UserName;
	@JsonProperty("p_pk_Ans")
	private String p_pk_Ans;
	@JsonProperty("pk_user")
	private int pk_user;
	@JsonProperty("trueName")
	private String trueName;
	@JsonProperty("userName")
	private String userName;
	
	
	public String getANS_CONTENT() {
		return ANS_CONTENT;
	}
	public void setANS_CONTENT(String aNS_CONTENT) {
		ANS_CONTENT = aNS_CONTENT;
	}
	public int getANS_STATE() {
		return ANS_STATE;
	}
	public void setANS_STATE(int aNS_STATE) {
		ANS_STATE = aNS_STATE;
	}
	public int getBAD_NUM() {
		return BAD_NUM;
	}
	public void setBAD_NUM(int bAD_NUM) {
		BAD_NUM = bAD_NUM;
	}
	public int getGOOD_NUM() {
		return GOOD_NUM;
	}
	public void setGOOD_NUM(int gOOD_NUM) {
		GOOD_NUM = gOOD_NUM;
	}
	public String getPK_Ans() {
		return PK_Ans;
	}
	public void setPK_Ans(String pK_Ans) {
		PK_Ans = pK_Ans;
	}
	public String getPK_Que() {
		return PK_Que;
	}
	public void setPK_Que(String pK_Que) {
		PK_Que = pK_Que;
	}
	public String getAnsTime() {
		return ansTime;
	}
	public void setAnsTime(String ansTime) {
		this.ansTime = ansTime;
	}
	public int getIs_child() {
		return is_child;
	}
	public void setIs_child(int is_child) {
		this.is_child = is_child;
	}
	public int getIs_havechild() {
		return is_havechild;
	}
	public void setIs_havechild(int is_havechild) {
		this.is_havechild = is_havechild;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getP_Nickname() {
		return p_Nickname;
	}
	public void setP_Nickname(String p_Nickname) {
		this.p_Nickname = p_Nickname;
	}
	public int getP_Pk_user() {
		return p_Pk_user;
	}
	public void setP_Pk_user(int p_Pk_user) {
		this.p_Pk_user = p_Pk_user;
	}
	public String getP_TrueName() {
		return p_TrueName;
	}
	public void setP_TrueName(String p_TrueName) {
		this.p_TrueName = p_TrueName;
	}
	public String getP_UserName() {
		return p_UserName;
	}
	public void setP_UserName(String p_UserName) {
		this.p_UserName = p_UserName;
	}
	public String getP_pk_Ans() {
		return p_pk_Ans;
	}
	public void setP_pk_Ans(String p_pk_Ans) {
		this.p_pk_Ans = p_pk_Ans;
	}
	public int getPk_user() {
		return pk_user;
	}
	public void setPk_user(int pk_user) {
		this.pk_user = pk_user;
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
	
	
}
