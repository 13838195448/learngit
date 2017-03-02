package com.mpyf.lening.interfaces.bean.Parame;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User extends ParameBase {

	/**
	 * �û�ID
	 */
	@JsonProperty("pk_user")
	private int pk_user;

	/**
	 * �û���
	 */
	@JsonProperty("username")
	private String username;

	/**
	 * ���ε�¼��KEY
	 */
	@JsonProperty("key")
	private String key;

	/**
	 * �ǳ�
	 */
	@JsonProperty("nickname")
	private String nickname;

	/**
	 * ��ʵ����
	 */
	@JsonProperty("truename")
	private String truename;

	/**
	 * ����
	 */
	@JsonProperty("email")
	private String email;

	/**
	 * �绰
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
	 * ���
	 */
	@JsonProperty("introduce")
	private String introduce;

	/**
	 * �ó�����
	 */
	@JsonProperty("field")
	private String field;

	/**
	 * ��ѧרҵ
	 */
	@JsonProperty("professional")
	private String professional;

	/**
	 * ��������
	 */
	@JsonProperty("deptname")
	private String deptname;

	/**
	 * �Ա�[Byte] 1Ů��2��
	 */
	@JsonProperty("sex")
	private int sex;

	/**
	 * ѧ�� 1.��ר���� 2.��ר 3.���� 4.˶ʿ�о��� 5.��ʿ�о���
	 */
	@JsonProperty("education")
	private int education;

	/**
	 * �����ҵʱ��
	 */
	@JsonProperty("softTime")
	private String softTime;
	/**
	 * �����ƺ�
	 */
	@JsonProperty("honor_name")
	private String honor_name;
	/**
	 * ����ͼƬ��ַ
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
