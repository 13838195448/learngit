package com.mpyf.lening.interfaces.bean.Parame;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Register extends ParameBase {
	// ע�����
	@JsonProperty("reg_org")
	private String reg_org;
	// ֤������
	@JsonProperty("idcard")
	private String idcard;
	// ��ʵ����
	@JsonProperty("truename")
	private String truename;
	// ����
	@JsonProperty("password")
	private String password;
	// ����
	@JsonProperty("email")
	private String email;
	// �ֻ�
	@JsonProperty("mphone")
	private String mphone;

	public String getReg_org() {
		return reg_org;
	}

	public void setReg_org(String reg_org) {
		this.reg_org = reg_org;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getTruename() {
		return truename;
	}

	public void setTruename(String truename) {
		this.truename = truename;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMphone() {
		return mphone;
	}

	public void setMphone(String mphone) {
		this.mphone = mphone;
	}

}
