package com.mpyf.lening.interfaces.bean.Parame;

import com.fasterxml.jackson.annotation.JsonProperty;

public class QADate {
	// ������
	@JsonProperty("adopt")
	private String adopt;
	/**
	 * ������
	 */
	@JsonProperty("goodsnum")
	private int goodsnum;
	/**
	 * ����ֵ
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
