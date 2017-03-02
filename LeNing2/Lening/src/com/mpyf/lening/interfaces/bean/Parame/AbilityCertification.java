package com.mpyf.lening.interfaces.bean.Parame;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AbilityCertification extends ParameBase{

	
	@JsonProperty("PK_Cer")
	private String PK_Cer;
	
	@JsonProperty("photoName")
	private String photoName;
	
	public String getPhotoName() {
		return photoName;
	}

	public void setPhotoName(String photoName) {
		this.photoName = photoName;
	}

	@JsonProperty("cer_Name")
	private String cer_Name;
	
	@JsonProperty("pk_user")
	private int pk_user;
	
	@JsonProperty("YWFX")
	private String YWFX;
	
	@JsonProperty("ZYLY")
	private String ZYLY;
	
	@JsonProperty("CPYY")
	private String CPYY;
	
	@JsonProperty("RZJB")
	private String RZJB;
	
	@JsonProperty("photo")
	private String photo;
	
	@JsonProperty("payInf")
	private boolean payInf;
	
	@JsonProperty("bmzt")
	private byte bmzt;
	
	@JsonProperty("bmkc")
	private String bmkc;
	
	@JsonProperty("amount")
	private int amount;
	
	@JsonProperty("education")
	private int education;

	public String getPK_Cer() {
		return PK_Cer;
	}

	public void setPK_Cer(String pK_Cer) {
		PK_Cer = pK_Cer;
	}

	public String getCer_Name() {
		return cer_Name;
	}

	public void setCer_Name(String cer_Name) {
		this.cer_Name = cer_Name;
	}

	public int getPk_user() {
		return pk_user;
	}

	public void setPk_user(int pk_user) {
		this.pk_user = pk_user;
	}

	public String getYWFX() {
		return YWFX;
	}

	public void setYWFX(String yWFX) {
		YWFX = yWFX;
	}

	public String getZYLY() {
		return ZYLY;
	}

	public void setZYLY(String zYLY) {
		ZYLY = zYLY;
	}

	public String getCPYY() {
		return CPYY;
	}

	public void setCPYY(String cPYY) {
		CPYY = cPYY;
	}

	public String getRZJB() {
		return RZJB;
	}

	public void setRZJB(String rZJB) {
		RZJB = rZJB;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public boolean getPayInf() {
		return payInf;
	}

	public void setPayInf(boolean payInf) {
		this.payInf = payInf;
	}

	public byte getBmzt() {
		return bmzt;
	}

	public void setBmzt(byte bmzt) {
		this.bmzt = bmzt;
	}

	public String getBmkc() {
		return bmkc;
	}

	public void setBmkc(String bmkc) {
		this.bmkc = bmkc;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public int getEducation() {
		return education;
	}

	public void setEducation(int education) {
		this.education = education;
	}

	@Override
	public String toString() {
		return "AbilityCertification [PK_Cer=" + PK_Cer + ", cer_Name="
				+ cer_Name + ", pk_user=" + pk_user + ", YWFX=" + YWFX
				+ ", ZYLY=" + ZYLY + ", CPYY=" + CPYY + ", RZJB=" + RZJB
				+ ", photo=" + photo + ", payInf=" + payInf + ", bmzt=" + bmzt
				+ ", bmkc=" + bmkc + ", amount=" + amount + ", education="
				+ education + ", getPK_Cer()=" + getPK_Cer()
				+ ", getCer_Name()=" + getCer_Name() + ", getPk_user()="
				+ getPk_user() + ", getYWFX()=" + getYWFX() + ", getZYLY()="
				+ getZYLY() + ", getCPYY()=" + getCPYY() + ", getRZJB()="
				+ getRZJB() + ", getPhoto()=" + getPhoto() + ", getPayInf()="
				+ getPayInf() + ", getBmzt()=" + getBmzt() + ", getBmkc()="
				+ getBmkc() + ", getAmount()=" + getAmount()
				+ ", getEducation()=" + getEducation() + ", getTime()="
				+ getTime() + ", getSourcefrom()=" + getSourcefrom()
				+ ", getToken()=" + getToken() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}

	
}
