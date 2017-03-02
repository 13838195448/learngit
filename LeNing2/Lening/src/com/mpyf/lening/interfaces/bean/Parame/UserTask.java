package com.mpyf.lening.interfaces.bean.Parame;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserTask {

	/**
	 * 用户任务
	 */
	@JsonProperty("pkTask")
	private String pkTask;
	/**
	 * 用户
	 */
	@JsonProperty("userId")
	private int userId;
	/**
	 * 任务名称
	 */
	@JsonProperty("taskName")
	private String taskName;
	/**
	 * 任务所需完成数量
	 */
	@JsonProperty("taskNum")
	private int taskNum;
	/**
	 * 任务类型1日回答数量 2日采纳数量，3累计采纳
	 */
	@JsonProperty("taskType")
	private int taskType;
	/**
	 * 任务状态 1未完成， 2完成未领取，3已领 ，4全天任务完成
	 */
	@JsonProperty("taskState")
	private int taskState;
	/**
	 * 任务完成情况
	 */
	@JsonProperty("taskInfo")
	private String taskInfo;
	/**
	 * 完成领取乐币数
	 */
	@JsonProperty("lcoin")
	private int lcoin;
	/**
	 * 完成领取金币数
	 */
	@JsonProperty("gcoin")
	private int gcoin;
	/**
	 * 完成领取经验数
	 */
	@JsonProperty("hcoin")
	private int hcoin;

	public String getPkTask() {
		return pkTask;
	}

	public void setPkTask(String pkTask) {
		this.pkTask = pkTask;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public int getTaskNum() {
		return taskNum;
	}

	public void setTaskNum(int taskNum) {
		this.taskNum = taskNum;
	}

	public int getTaskType() {
		return taskType;
	}

	public void setTaskType(int taskType) {
		this.taskType = taskType;
	}

	public int getTaskState() {
		return taskState;
	}

	public void setTaskState(int taskState) {
		this.taskState = taskState;
	}

	public String getTaskInfo() {
		return taskInfo;
	}

	public void setTaskInfo(String taskInfo) {
		this.taskInfo = taskInfo;
	}

	public int getLcoin() {
		return lcoin;
	}

	public void setLcoin(int lcoin) {
		this.lcoin = lcoin;
	}

	public int getGcoin() {
		return gcoin;
	}

	public void setGcoin(int gcoin) {
		this.gcoin = gcoin;
	}

	public int getHcoin() {
		return hcoin;
	}

	public void setHcoin(int hcoin) {
		this.hcoin = hcoin;
	}

}
