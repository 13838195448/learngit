package com.mpyf.lening.interfaces.bean.Parame;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserTask {

	/**
	 * �û�����
	 */
	@JsonProperty("pkTask")
	private String pkTask;
	/**
	 * �û�
	 */
	@JsonProperty("userId")
	private int userId;
	/**
	 * ��������
	 */
	@JsonProperty("taskName")
	private String taskName;
	/**
	 * ���������������
	 */
	@JsonProperty("taskNum")
	private int taskNum;
	/**
	 * ��������1�ջش����� 2�ղ���������3�ۼƲ���
	 */
	@JsonProperty("taskType")
	private int taskType;
	/**
	 * ����״̬ 1δ��ɣ� 2���δ��ȡ��3���� ��4ȫ���������
	 */
	@JsonProperty("taskState")
	private int taskState;
	/**
	 * ����������
	 */
	@JsonProperty("taskInfo")
	private String taskInfo;
	/**
	 * �����ȡ�ֱ���
	 */
	@JsonProperty("lcoin")
	private int lcoin;
	/**
	 * �����ȡ�����
	 */
	@JsonProperty("gcoin")
	private int gcoin;
	/**
	 * �����ȡ������
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
