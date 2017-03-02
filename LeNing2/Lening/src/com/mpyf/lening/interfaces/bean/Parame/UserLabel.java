package com.mpyf.lening.interfaces.bean.Parame;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserLabel extends ParameBase {

	/**
	 * 用户
	 */
	@JsonProperty("userId")
	private Integer userId;
	/**
	 * 标签数
	 */
	@JsonProperty("label_num")
	private Integer label_num;
	/**
	 * 标签1id
	 */
	@JsonProperty("label1_id")
	private String label1_id;
	/**
	 * 标签2id
	 */
	@JsonProperty("label2_id")
	private String label2_id;
	/**
	 * 标签3id
	 */
	@JsonProperty("label3_id")
	private String label3_id;
	/**
	 * 标签1名称
	 */
	@JsonProperty("label1_name")
	private String label1_name;
	/**
	 * 标签2名称
	 */
	@JsonProperty("label2_name")
	private String label2_name;
	/**
	 * 标签3名称
	 */
	@JsonProperty("label3_name")
	private String label3_name;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getLabel_num() {
		return label_num;
	}

	public void setLabel_num(Integer label_num) {
		this.label_num = label_num;
	}

	public String getLabel1_id() {
		return label1_id;
	}

	public void setLabel1_id(String label1_id) {
		this.label1_id = label1_id;
	}

	public String getLabel2_id() {
		return label2_id;
	}

	public void setLabel2_id(String label2_id) {
		this.label2_id = label2_id;
	}

	public String getLabel3_id() {
		return label3_id;
	}

	public void setLabel3_id(String label3_id) {
		this.label3_id = label3_id;
	}

	public String getLabel1_name() {
		return label1_name;
	}

	public void setLabel1_name(String label1_name) {
		this.label1_name = label1_name;
	}

	public String getLabel2_name() {
		return label2_name;
	}

	public void setLabel2_name(String label2_name) {
		this.label2_name = label2_name;
	}

	public String getLabel3_name() {
		return label3_name;
	}

	public void setLabel3_name(String label3_name) {
		this.label3_name = label3_name;
	}

}
