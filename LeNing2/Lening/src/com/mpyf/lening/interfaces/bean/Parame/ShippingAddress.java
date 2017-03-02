package com.mpyf.lening.interfaces.bean.Parame;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ShippingAddress extends ParameBase {
	// ��ַ����
	@JsonProperty("pk_adr")
	private String pk_adr;
	// �û�id
	@JsonProperty("pk_user")
	private int pk_user;
	// ��ַ
	@JsonProperty("address")
	private String address;
	// �ջ���
	@JsonProperty("consignee")
	private String consignee;
	// ��ϵ��ʽ
	@JsonProperty("mphone")
	private String mphone;
	// �Ƿ�Ĭ�ϵ�ַ 0��1��
	@JsonProperty("is_default")
	private int is_default;

	public String getPk_adr() {
		return pk_adr;
	}

	public void setPk_adr(String pk_adr) {
		this.pk_adr = pk_adr;
	}

	public int getPk_user() {
		return pk_user;
	}

	public void setPk_user(int pk_user) {
		this.pk_user = pk_user;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getConsignee() {
		return consignee;
	}

	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}

	public String getMphone() {
		return mphone;
	}

	public void setMphone(String mphone) {
		this.mphone = mphone;
	}

	public int getIs_default() {
		return is_default;
	}

	public void setIs_default(int is_default) {
		this.is_default = is_default;
	}

}
