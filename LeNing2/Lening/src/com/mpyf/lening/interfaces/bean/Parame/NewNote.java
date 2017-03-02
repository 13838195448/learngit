package com.mpyf.lening.interfaces.bean.Parame;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NewNote extends ParameBase{

	@JsonProperty("PK_Note")
	private String pK_Note;
	
	@JsonProperty("PK_Course")
	private String pK_Course;
	
	@JsonProperty("PK_Res")
	private String pK_Res;
	
	@JsonProperty("Pk_user")
	private int pk_user;
	
	@JsonProperty("NOTE_TITLE")
	private String nOTE_TITLE;
	
	@JsonProperty("NOTE_CONTENT")
	private String nOTE_CONTENT;
	
	@JsonProperty("NOTE_Time")
	private String nOTE_Time;

	public String getPK_Note() {
		return pK_Note;
	}

	public void setPK_Note(String pK_Note) {
		this.pK_Note = pK_Note;
	}

	public String getPK_Course() {
		return pK_Course;
	}

	public void setPK_Course(String pK_Course) {
		this.pK_Course = pK_Course;
	}

	public String getPK_Res() {
		return pK_Res;
	}

	public void setPK_Res(String pK_Res) {
		this.pK_Res = pK_Res;
	}

	public int getPk_user() {
		return pk_user;
	}

	public void setPk_user(int pk_user) {
		this.pk_user = pk_user;
	}

	public String getNOTE_TITLE() {
		return nOTE_TITLE;
	}

	public void setNOTE_TITLE(String nOTE_TITLE) {
		this.nOTE_TITLE = nOTE_TITLE;
	}

	public String getNOTE_CONTENT() {
		return nOTE_CONTENT;
	}

	public void setNOTE_CONTENT(String nOTE_CONTENT) {
		this.nOTE_CONTENT = nOTE_CONTENT;
	}

	public String getNOTE_Time() {
		return nOTE_Time;
	}

	public void setNOTE_Time(String nOTE_Time) {
		this.nOTE_Time = nOTE_Time;
	}

	
}
