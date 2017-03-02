package com.mpyf.lening.interfaces.bean.Parame;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Note extends ParameBase{

	@JsonProperty("PK_Note")
	private String PK_Note;
	
	@JsonProperty("PK_Course")
	private String PK_Course;
	
	@JsonProperty("PK_Res")
	private String PK_Res;
	
	@JsonProperty("pk_user")
	private int pk_user;
	
	@JsonProperty("NOTE_TITLE")
	private String NOTE_TITLE;
	
	@JsonProperty("NOTE_CONTENT")
	private String NOTE_CONTENT;
	
	@JsonProperty("NOTE_Time")
	private String NOTE_Time;

	public String getPK_Note() {
		return PK_Note;
	}

	public void setPK_Note(String pK_Note) {
		PK_Note = pK_Note;
	}

	public String getPK_Course() {
		return PK_Course;
	}

	public void setPK_Course(String pK_Course) {
		PK_Course = pK_Course;
	}

	public String getPK_Res() {
		return PK_Res;
	}

	public void setPK_Res(String pK_Res) {
		PK_Res = pK_Res;
	}

	public int getPk_user() {
		return pk_user;
	}

	public void setPk_user(int pk_user) {
		this.pk_user = pk_user;
	}

	public String getNOTE_TITLE() {
		return NOTE_TITLE;
	}

	public void setNOTE_TITLE(String nOTE_TITLE) {
		NOTE_TITLE = nOTE_TITLE;
	}

	public String getNOTE_CONTENT() {
		return NOTE_CONTENT;
	}

	public void setNOTE_CONTENT(String nOTE_CONTENT) {
		NOTE_CONTENT = nOTE_CONTENT;
	}

	public String getNOTE_Time() {
		return NOTE_Time;
	}

	public void setNOTE_Time(String nOTE_Time) {
		NOTE_Time = nOTE_Time;
	}

	
}
