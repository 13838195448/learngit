package com.mpyf.lening.interfaces.bean.Parame;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SignDate {

	/**
	 * ǩ���·�
	 */
	@JsonProperty("month")
	private int month;
	/**
	 * ǩ����
	 */
	@JsonProperty("day")
	private String day;
	/**
	 * �Ƿ�ǩ��[Int32] 0�Ƿ� 1����
	 */
	@JsonProperty("isSign")
	private int isSign;
	/**
	 * �Ƿ��ܲ�ǩ[Int32] 0�Ƿ� 1����
	 */
	@JsonProperty("iscansign")
	private int iscansign;

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public int getIsSign() {
		return isSign;
	}

	public void setIsSign(int isSign) {
		this.isSign = isSign;
	}

	public int getIscansign() {
		return iscansign;
	}

	public void setIscansign(int iscansign) {
		this.iscansign = iscansign;
	}

}
