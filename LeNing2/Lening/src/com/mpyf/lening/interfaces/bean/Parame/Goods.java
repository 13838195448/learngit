package com.mpyf.lening.interfaces.bean.Parame;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Goods extends ParameBase {

	/**
	 * 商品主键[String]
	 */
	@JsonProperty("pk_goods")
	private String pk_goods;
	/**
	 * 商品名称[String]
	 */
	@JsonProperty("goodsName")
	private String goodsName;
	/**
	 * 库存[Int32]
	 */
	@JsonProperty("inventory")
	private String inventory;
	/**
	 * 购买人数[Int32]
	 */
	@JsonProperty("buy_num")
	private String buy_num;
	/**
	 * 月购买人数[Int32]
	 */
	@JsonProperty("mbuy_num")
	private String mbuy_num;
	/**
	 * 图片地址[String]
	 */
	@JsonProperty("PicUrl")
	private String PicUrl;
	/**
	 * 平均评价[String]
	 */
	@JsonProperty("eval")
	private String eval;
	/**
	 * 简介[String]
	 */
	@JsonProperty("remark")
	private String remark;

	/**
	 * 购买方式[Int32] 1乐币，2金币
	 */
	@JsonProperty("buyWay")
	private String buyWay;
	/**
	 * 售价[Int32]
	 */
	@JsonProperty("amount")
	private String amount;

	/**
	 * 上线时间[String] 2016-03-27
	 */
	@JsonProperty("onlineTime")
	private String onlineTime;

	public String getPk_goods() {
		return pk_goods;
	}

	public void setPk_goods(String pk_goods) {
		this.pk_goods = pk_goods;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getInventory() {
		return inventory;
	}

	public void setInventory(String inventory) {
		this.inventory = inventory;
	}

	public String getBuy_num() {
		return buy_num;
	}

	public void setBuy_num(String buy_num) {
		this.buy_num = buy_num;
	}

	public String getMbuy_num() {
		return mbuy_num;
	}

	public void setMbuy_num(String mbuy_num) {
		this.mbuy_num = mbuy_num;
	}

	public String getPicUrl() {
		return PicUrl;
	}

	public void setPicUrl(String picUrl) {
		PicUrl = picUrl;
	}

	public String getEval() {
		return eval;
	}

	public void setEval(String eval) {
		this.eval = eval;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getBuyWay() {
		return buyWay;
	}

	public void setBuyWay(String buyWay) {
		this.buyWay = buyWay;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getOnlineTime() {
		return onlineTime;
	}

	public void setOnlineTime(String onlineTime) {
		this.onlineTime = onlineTime;
	}

}
