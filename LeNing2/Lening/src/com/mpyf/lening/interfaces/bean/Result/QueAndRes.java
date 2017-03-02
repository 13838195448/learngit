package com.mpyf.lening.interfaces.bean.Result;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mpyf.lening.interfaces.bean.Parame.ItemUser;
import com.mpyf.lening.interfaces.bean.Parame.ParameBase;

/**
 * @author 试题及选项
 *
 */
public class QueAndRes extends ParameBase{

	@JsonProperty("IS_Right")
	private Boolean IS_Right;

	public Boolean getIS_Right() {
		return IS_Right;
	}

	public void setIS_Right(Boolean iS_Right) {
		IS_Right = iS_Right;
	}

	
	@JsonProperty("PK_Paper")
	private String PK_Paper;

	public String getPK_Paper() {
		return PK_Paper;
	}

	public void setPK_Paper(String pK_Paper) {
		PK_Paper = pK_Paper;
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

	@JsonProperty("Que_content")
	private String Que_content;

	public String getQue_content() {
		return Que_content;
	}

	public void setQue_content(String que_content) {
		Que_content = que_content;
	}

	@JsonProperty("Que_type")
	private Integer Que_type;

	public Integer getQue_type() {
		return Que_type;
	}

	public void setQue_type(int que_type) {
		Que_type = que_type;
	}

	@JsonProperty("Que_Score")
	private Integer Que_Score;

	public Integer getQue_Score() {
		return Que_Score;
	}

	public void setQue_Score(int que_Score) {
		Que_Score = que_Score;
	}

	@JsonProperty("item")
	private List<Item> item;

	public List<Item> getItem() {
		return item;
	}

	public void setItem(List<Item> item) {
		this.item = item;
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
