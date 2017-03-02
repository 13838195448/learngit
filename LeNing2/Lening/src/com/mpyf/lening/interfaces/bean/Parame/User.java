package com.mpyf.lening.interfaces.bean.Parame;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User extends ParameBase {

	/**
	 * 用户ID
	 */
	@JsonProperty("pk_user")
	private int pk_user;

	/**
	 * 用户名
	 */
	@JsonProperty("username")
	private String username;

	/**
	 * 本次登录的KEY
	 */
	@JsonProperty("key")
	private String key;

	/**
	 * 昵称
	 */
	@JsonProperty("nickname")
	private String nickname;

	/**
	 * 真实姓名
	 */
	@JsonProperty("truename")
	private String truename;

	/**
	 * 邮箱
	 */
	@JsonProperty("email")
	private String email;

	/**
	 * 电话
	 */
	@JsonProperty("mphone")
	private String mphone;

	public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

	/**
	 * 简介
	 */
	@JsonProperty("introduce")
	private String introduce;

	/**
	 * 擅长领域
	 */
	@JsonProperty("field")
	private String field;

	/**
	 * 所学专业
	 */
	@JsonProperty("professional")
	private String professional;

	/**
	 * 所属机构
	 */
	@JsonProperty("deptname")
	private String deptname;

	/**
	 * 性别[Byte] 1女，2男
	 */
	@JsonProperty("sex")
	private int sex;

	/**
	 * 学历 1.大专以下 2.大专 3.本科 4.硕士研究生 5.博士研究生
	 */
	@JsonProperty("education")
	private int education;

	/**
	 * 软件从业时间
	 */
	@JsonProperty("softTime")
	private String softTime;
	/**
	 * 荣誉称号
	 */
	@JsonProperty("honor_name")
	private String honor_name;
	/**
	 * 荣誉图片地址
	 */
	@JsonProperty("honor_pic")
	private String honor_pic;

	public String getHonor_name() {
		return honor_name;
	}

	public void setHonor_name(String honor_name) {
		this.honor_name = honor_name;
	}

	public String getHonor_pic() {
		return honor_pic;
	}

	public void setHonor_pic(String honor_pic) {
		this.honor_pic = honor_pic;
	}

	public String getSoftTime() {
		return softTime;
	}

	public void setSoftTime(String softTime) {
		this.softTime = softTime;
	}

	public int getEducation() {
		return education;
	}

	public void setEducation(int education) {
		this.education = education;
	}

	public int getPk_user() {
		return pk_user;
	}

	public void setPk_user(int pk_user) {
		this.pk_user = pk_user;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getTruename() {
		return truename;
	}

	public void setTruename(String truename) {
		this.truename = truename;
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

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getProfessional() {
		return professional;
	}

	public void setProfessional(String professional) {
		this.professional = professional;
	}

	public String getDeptname() {
		return deptname;
	}

	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

}
