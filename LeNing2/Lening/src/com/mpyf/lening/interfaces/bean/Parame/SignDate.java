package com.mpyf.lening.interfaces.bean.Parame;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SignDate {

	/**
	 * 签到月份
	 */
	@JsonProperty("month")
	private int month;
	/**
	 * 签到日
	 */
	@JsonProperty("day")
	private String day;
	/**
	 * 是否签到[Int32] 0是否 1是是
	 */
	@JsonProperty("isSign")
	private int isSign;
	/**
	 * 是否能补签[Int32] 0是否 1是是
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
