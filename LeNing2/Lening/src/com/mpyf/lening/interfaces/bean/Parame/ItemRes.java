package com.mpyf.lening.interfaces.bean.Parame;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author  ‘Ã‚¥∞∏
 *
 */
public class ItemRes extends ParameBase {

	@JsonProperty("PK_Paper")
	private String PK_Paper;

	public String getPK_Paper() {
		return PK_Paper;
	}

	public void setPK_Paper(String pK_Paper) {
		PK_Paper = pK_Paper;
	}

	@JsonProperty("PK_TruePaper")
	private String PK_TruePaper;

	

	public String getPK_TruePaper() {
		return PK_TruePaper;
	}

	public void setPK_TruePaper(String pK_TruePaper) {
		PK_TruePaper = pK_TruePaper;
	}

	@JsonProperty("PK_Exam")
	private String PK_Exam;

	public String getPK_Exam() {
		return PK_Exam;
	}

	public void setPK_Exam(String pK_Exam) {
		PK_Exam = pK_Exam;
	}

	@JsonProperty("PK_Que")
	private String PK_Que;

	

	public String getPK_Que() {
		return PK_Que;
	}

	public void setPK_Que(String pK_Que) {
		PK_Que = pK_Que;
	}

	@JsonProperty("itemUser")
	private List<ItemUser> itemUser;

	public List<ItemUser> getItemUser() {
		return itemUser;
	}

	public void setItemUser(List<ItemUser> itemUser) {
		this.itemUser = itemUser;
	}

}
