package com.mpyf.lening.interfaces.bean.Result;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mpyf.lening.interfaces.bean.Parame.ParameBase;

/**
 * @author вт╡Б
 * 
 */
public class ExamSelf extends ParameBase{
	
	public String PK_ExamSelf;

	public String getPK_ExamSelf() {
		return PK_ExamSelf;
	}

	public void setPK_ExamSelf(String pK_ExamSelf) {
		PK_ExamSelf = pK_ExamSelf;
	}

	@JsonProperty("buyWay")
	public int buyWay;

	public int getBuyWay() {
		return buyWay;
	}

	public void setBuyWay(int buyWay) {
		buyWay = buyWay;
	}

	@JsonProperty("exam_Name")
	public String exam_Name;

	public String getExam_Name() {
		return exam_Name;
	}

	public void setExam_Name(String exam_Name) {
		exam_Name = exam_Name;
	}

	@JsonProperty("sta_Time")
	public String sta_Time;

	public String getSta_Time() {
		return sta_Time;
	}

	public void setSta_Time(String sta_Time) {
		sta_Time = sta_Time;
	}

	@JsonProperty("end_Time")
	public String end_Time;

	public String getEnd_Time() {
		return end_Time;
	}

	public void setEnd_Time(String end_Time) {
		end_Time = end_Time;
	}

	@JsonProperty("exam_Long")
	public int exam_Long;

	public int getExam_Long() {
		return exam_Long;
	}

	public void setExam_Long(int exam_Long) {
		exam_Long = exam_Long;
	}

	@JsonProperty("exam_State")
	public int exam_State;

	public int getExam_State() {
		return exam_State;
	}

	public void setExam_State(int exam_State) {
		exam_State = exam_State;
	}

	@JsonProperty("score")
	public int score;

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		score = score;
	}

	@JsonProperty("amount")
	public int amount;

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		amount = amount;
	}

}
