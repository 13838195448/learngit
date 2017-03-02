package com.mpyf.lening.interfaces.bean.Parame;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class Vote1 extends ParameBase {

	/**
	 * 投票标题[String]
	 */
	@JsonProperty("vote_title")
	private String vote_title;

	/**
	 * 图内容[byte[] ] 输入用
	 */
	@JsonProperty("pic")
	@JsonInclude(Include.NON_NULL)
	private byte[] pic;

	/**
	 * 投票时间[String] 格式2016-11-17 13:44:13
	 */
	@JsonProperty("vote_time")
	private String vote_time;

	/**
	 * 投票人数[Int32]
	 */
	@JsonProperty("vote_num")
	private int vote_num;

	/**
	 * 悬赏方式[Int32]0不悬赏1乐币2金币
	 */
	@JsonProperty("reward_type")
	private int reward_type;
	/**
	 * 悬赏数[Int32]
	 */
	@JsonProperty("reward_num")
	private int reward_num;
	/**
	 * 每人悬赏[Int32]
	 */
	@JsonProperty("reward_every")
	private int reward_every;
	/**
	 * 选项类型[Int32] 0单选，1多选
	 */
	@JsonProperty("option_type")
	private int option_type;
	/**
	 * 选项个数[Int32]
	 */
	@JsonProperty("option_num")
	private int option_num;
	/**
	 * 选项a内容[String]
	 */
	@JsonProperty("option_a")
	private String option_a;
	/**
	 * 选项b内容[String]
	 */
	@JsonProperty("option_b")
	private String option_b;
	/**
	 * 选项c内容[String]
	 */
	@JsonProperty("option_c")
	private String option_c;
	/**
	 * 选项d内容[String]
	 */
	@JsonProperty("option_d")
	private String option_d;
	/**
	 * 选项e内容[String]
	 */
	@JsonProperty("option_e")
	private String option_e;
	/**
	 * 选项f内容[String]
	 */
	@JsonProperty("option_f")
	private String option_f;
	/**
	 * 选项g内容[String]
	 */
	@JsonProperty("option_g")
	private String option_g;
	/**
	 * 选项h内容[String]
	 */
	@JsonProperty("option_h")
	private String option_h;
	/**
	 * 选项i内容[String]
	 */
	@JsonProperty("option_i")
	private String option_i;
	/**
	 * 选项j内容[String]
	 */
	@JsonProperty("option_j")
	private String option_j;

	public String getVote_title() {
		return vote_title;
	}

	public void setVote_title(String vote_title) {
		this.vote_title = vote_title;
	}

	public byte[] getPic() {
		return pic;
	}

	public void setPic(byte[] pic) {
		this.pic = pic;
	}

	public String getVote_time() {
		return vote_time;
	}

	public void setVote_time(String vote_time) {
		this.vote_time = vote_time;
	}

	public int getVote_num() {
		return vote_num;
	}

	public void setVote_num(int vote_num) {
		this.vote_num = vote_num;
	}

	public int getReward_type() {
		return reward_type;
	}

	public void setReward_type(int reward_type) {
		this.reward_type = reward_type;
	}

	public int getReward_num() {
		return reward_num;
	}

	public void setReward_num(int reward_num) {
		this.reward_num = reward_num;
	}

	public int getReward_every() {
		return reward_every;
	}

	public void setReward_every(int reward_every) {
		this.reward_every = reward_every;
	}

	public int getOption_type() {
		return option_type;
	}

	public void setOption_type(int option_type) {
		this.option_type = option_type;
	}

	public int getOption_num() {
		return option_num;
	}

	public void setOption_num(int option_num) {
		this.option_num = option_num;
	}

	public String getOption_a() {
		return option_a;
	}

	public void setOption_a(String option_a) {
		this.option_a = option_a;
	}

	public String getOption_b() {
		return option_b;
	}

	public void setOption_b(String option_b) {
		this.option_b = option_b;
	}

	public String getOption_c() {
		return option_c;
	}

	public void setOption_c(String option_c) {
		this.option_c = option_c;
	}

	public String getOption_d() {
		return option_d;
	}

	public void setOption_d(String option_d) {
		this.option_d = option_d;
	}

	public String getOption_e() {
		return option_e;
	}

	public void setOption_e(String option_e) {
		this.option_e = option_e;
	}

	public String getOption_f() {
		return option_f;
	}

	public void setOption_f(String option_f) {
		this.option_f = option_f;
	}

	public String getOption_g() {
		return option_g;
	}

	public void setOption_g(String option_g) {
		this.option_g = option_g;
	}

	public String getOption_h() {
		return option_h;
	}

	public void setOption_h(String option_h) {
		this.option_h = option_h;
	}

	public String getOption_i() {
		return option_i;
	}

	public void setOption_i(String option_i) {
		this.option_i = option_i;
	}

	public String getOption_j() {
		return option_j;
	}

	public void setOption_j(String option_j) {
		this.option_j = option_j;
	}

}
