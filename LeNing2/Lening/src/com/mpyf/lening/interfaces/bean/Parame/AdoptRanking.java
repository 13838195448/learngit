package com.mpyf.lening.interfaces.bean.Parame;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AdoptRanking {
	// 用户
	@JsonProperty("userId")
	private int userId;
	// 真实姓名
	@JsonProperty("trueName")
	private String trueName;
	// 荣誉称号
	@JsonProperty("honor_name")
	private String honor_name;
	// 荣誉图片地址
	@JsonProperty("honor_pic")
	private String honor_pic;
	// 采纳数
	@JsonProperty("adoptnum")
	private int adoptnum;

	public int getUserId() {
		return userId;
	}

	public String getTrueName() {
		return trueName;
	}

	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

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

	public int getAdoptnum() {
		return adoptnum;
	}

	public void setAdoptnum(int adoptnum) {
		this.adoptnum = adoptnum;
	}

}
