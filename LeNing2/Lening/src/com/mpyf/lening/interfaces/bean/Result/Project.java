package com.mpyf.lening.interfaces.bean.Result;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Project {

	@JsonProperty("PK_Pro")
	public String PK_Pro;
	
	@JsonProperty("isExam")
	public boolean isExam;
	
	@JsonProperty("pro_Name")
	public String pro_Name;
	
	@JsonProperty("pro_pic_url")
	public String pro_pic_url;
	
	@JsonProperty("proInfo_pic_url")
	public String proInfo_pic_url;
	
	@JsonProperty("remark")
	public String remark;
	
	@JsonProperty("pro_Exam")
	public List<ExamSelf> pro_Exam;
	
	@JsonProperty("pro_Column")
	public List<ProColumn> pro_Column;
}
