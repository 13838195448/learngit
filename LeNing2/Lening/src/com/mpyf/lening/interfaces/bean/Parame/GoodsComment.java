package com.mpyf.lening.interfaces.bean.Parame;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GoodsComment extends ParameBase {

	// 评论主键
	@JsonProperty("pk_Com")
	private String pk_Com;
	// 商品主键
	@JsonProperty("pk_goods")
	private String pk_goods;
	// 订单主键
	@JsonProperty("pk_order")
	private String pk_order;
	// 评论内容
	@JsonProperty("com_Con")
	private String com_Con;
	// 评论级别
	@JsonProperty("com_Level")
	private int com_Level;

	public String getPk_Com() {
		return pk_Com;
	}

	public void setPk_Com(String pk_Com) {
		this.pk_Com = pk_Com;
	}

	public String getPk_goods() {
		return pk_goods;
	}

	public void setPk_goods(String pk_goods) {
		this.pk_goods = pk_goods;
	}

	public String getPk_order() {
		return pk_order;
	}

	public void setPk_order(String pk_order) {
		this.pk_order = pk_order;
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

}
