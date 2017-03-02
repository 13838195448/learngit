package com.mpyf.lening.interfaces.bean.Result;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author ÎÒµÄ¿¼ÊÔ
 *
 */
public class MyExam {
	
	
	@JsonProperty("PK_Exam")
	private String PK_Exam;

	public String getPK_Exam() {
		return PK_Exam;
	}

	public void setPK_Exam(String pK_Exam) {
		PK_Exam = pK_Exam;
	}

	@JsonProperty("Exam_Name")
	private String Exam_Name;

	public String getExam_Name() {
		return Exam_Name;
	}

	public void setExam_Name(String exam_Name) {
		Exam_Name = exam_Name;
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

	@JsonProperty("Exam_state")
	private Integer Exam_state;

	public Integer getExam_state() {
		return Exam_state;
	}

	public void setExam_state(Integer exam_state) {
		Exam_state = exam_state;
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
