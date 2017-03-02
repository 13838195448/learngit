package com.mpyf.lening.interfaces.bean.Result;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author ÎÒµÄ×Ô²â
 *
 */
public class MyExamSelf {
	@JsonProperty("PK_ExamSelf")
	private String PK_ExamSelf;

	public String getPK_ExamSelf() {
		return PK_ExamSelf;
	}

	public void setPK_ExamSelf(String pK_ExamSelf) {
		PK_ExamSelf = pK_ExamSelf;
	}
	@JsonProperty("Exam_Mame")
	private String Exam_Mame;

	public String getExam_Mame() {
		return Exam_Mame;
	}

	public void setExam_Mame(String exam_Mame) {
		Exam_Mame = exam_Mame;
	}
	@JsonProperty("Sta_Time")
	private String Sta_Time;

	public String getSta_Time() {
		return Sta_Time;
	}

	public void setSta_Time(String sta_Time) {
		Sta_Time = sta_Time;
	}
	@JsonProperty("End_Time")
	private String End_Time;

	public String getEnd_Time() {
		return End_Time;
	}

	public void setEnd_Time(String end_Time) {
		End_Time = end_Time;
	}
	@JsonProperty("Exam_Long")
	private Integer Exam_Long;

	public Integer getExam_Long() {
		return Exam_Long;
	}

	public void setExam_Long(Integer exam_Long) {
		Exam_Long = exam_Long;
	}
	@JsonProperty("Exam_Name")
	private Character Exam_Name;

	public Character getExam_Name() {
		return Exam_Name;
	}

	public void setExam_Name(Character exam_Name) {
		Exam_Name = exam_Name;
	}
	@JsonProperty("Score")
	private Integer Score;

	public Integer getScore() {
		return Score;
	}

	public void setScore(Integer score) {
		Score = score;
	}

}
