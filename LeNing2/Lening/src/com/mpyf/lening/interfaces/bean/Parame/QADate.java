package com.mpyf.lening.interfaces.bean.Parame;

import com.fasterxml.jackson.annotation.JsonProperty;

public class QADate {
	// 采纳率
	@JsonProperty("adopt")
	private String adopt;
	/**
	 * 点暂数
	 */
	@JsonProperty("goodsnum")
	private int goodsnum;
	/**
	 * 经验值
	 */
	@JsonProperty("honor")
	private int honor;

	public String getAdopt() {
		return adopt;
	}

	public void setAdopt(String adopt) {
		this.adopt = adopt;
	}

	public int getGoodsnum() {
		return goodsnum;
	}

	public void setGoodsnum(int goodsnum) {
		this.goodsnum = goodsnum;
	}

	public int getHonor() {
		return honor;
	}

	public void setHonor(int honor) {
		this.honor = honor;
	}

}
