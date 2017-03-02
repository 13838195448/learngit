package com.mpyf.lening.interfaces.bean.Parame;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Vote extends ParameBase {

	/**
	 * 投票主键[String]
	 */
	@JsonProperty("pk_vote")
	private String pk_vote;
	/**
	 * 投票标题[String]
	 */
	@JsonProperty("vote_title")
	private String vote_title;
	/**
	 * 图片地址[String]返回用
	 */
	@JsonProperty("vote_pic")
	private String vote_pic;
	/**
	 * 创建者ID[Int32]
	 */
	@JsonProperty("pk_user")
	private int pk_user;
	/**
	 * 用户名[String]
	 */
	@JsonProperty("UserName")
	private String UserName;
	/**
	 * 昵称[String]
	 */
	@JsonProperty("Nickname")
	private String Nickname;
	/**
	 * 真实姓名[String]
	 */
	@JsonProperty("TrueName")
	private String TrueName;
	/**
	 * 荣誉称号[String] [返回用]
	 */
	@JsonProperty("honor_name")
	private String honor_name;
	/**
	 * 荣誉称号图片地址[String] [返回用]
	 */
	@JsonProperty("honor_pic")
	private String honor_pic;

	/**
	 * 图内容[byte[] ] 输入用
	 */
	@JsonProperty("pic")
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
	 * 剩余悬赏[Int32]
	 */
	@JsonProperty("reward_residue")
	private int reward_residue;
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
	/**
	 * 是否投票[Int32] 0是否 1是是
	 */
	@JsonProperty("isVote")
	private int isVote;

	/**
	 * 用户选项[String]格式（a,c,e）
	 */
	@JsonProperty("user_option")
	private String user_option;
	/**
	 * 选项a投票结果[String]
	 */
	@JsonProperty("votes_a")
	private String votes_a;

	/**
	 * 选项b投票结果[String]
	 */
	@JsonProperty("votes_b")
	private String votes_b;
	/**
	 * 选项c投票结果[String]
	 */
	@JsonProperty("votes_c")
	private String votes_c;
	/**
	 * 选项d投票结果[String]
	 */
	@JsonProperty("votes_d")
	private String votes_d;
	/**
	 * 选项e投票结果[String]
	 */
	@JsonProperty("votes_e")
	private String votes_e;
	/**
	 * 选项f投票结果[String]
	 */
	@JsonProperty("votes_f")
	private String votes_f;
	/**
	 * 选项g投票结果[String]
	 */
	@JsonProperty("votes_g")
	private String votes_g;
	/**
	 * 选项h投票结果[String]
	 */
	@JsonProperty("votes_h")
	private String votes_h;
	/**
	 * 选项i投票结果[String]
	 */
	@JsonProperty("votes_i")
	private String votes_i;
	/**
	 * 选项j投票结果[String]
	 */
	@JsonProperty("votes_j")
	private String votes_j;

	public String getPk_vote() {
		return pk_vote;
	}

	public void setPk_vote(String pk_vote) {
		this.pk_vote = pk_vote;
	}

	public String getVote_title() {
		return vote_title;
	}

	public void setVote_title(String vote_title) {
		this.vote_title = vote_title;
	}

	public String getVote_pic() {
		return vote_pic;
	}

	public void setVote_pic(String vote_pic) {
		this.vote_pic = vote_pic;
	}

	public int getPk_user() {
		return pk_user;
	}

	public void setPk_user(int pk_user) {
		this.pk_user = pk_user;
	}

	public String getUserName() {
		return UserName;
	}

	public void setUserName(String userName) {
		UserName = userName;
	}

	public String getNickname() {
		return Nickname;
	}

	public void setNickname(String nickname) {
		Nickname = nickname;
	}

	public String getTrueName() {
		return TrueName;
	}

	public void setTrueName(String trueName) {
		TrueName = trueName;
	}

	public String getHonor_name() {
		return honor_name;
	}

	public void setHonor_name(String honor_name) {
		this.honor_name = honor_name;
	}

	public String getHonor_pic() {
		return honor_pic;
	}

	public void setHonor_pic(String honor_pic) {
		this.honor_pic = honor_pic;
	}

	public byte[] getPic() {
		return pic;
	}

	public void setPic(byte[] photoData) {
		this.pic = photoData;
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

	public int getReward_residue() {
		return reward_residue;
	}

	public void setReward_residue(int reward_residue) {
		this.reward_residue = reward_residue;
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

	public int getIsVote() {
		return isVote;
	}

	public void setIsVote(int isVote) {
		this.isVote = isVote;
	}

	public String getUser_option() {
		return user_option;
	}

	public void setUser_option(String user_option) {
		this.user_option = user_option;
	}

	public String getVotes_a() {
		return votes_a;
	}

	public void setVotes_a(String votes_a) {
		this.votes_a = votes_a;
	}

	public String getVotes_b() {
		return votes_b;
	}

	public void setVotes_b(String votes_b) {
		this.votes_b = votes_b;
	}

	public String getVotes_c() {
		return votes_c;
	}

	public void setVotes_c(String votes_c) {
		this.votes_c = votes_c;
	}

	public String getVotes_d() {
		return votes_d;
	}

	public void setVotes_d(String votes_d) {
		this.votes_d = votes_d;
	}

	public String getVotes_e() {
		return votes_e;
	}

	public void setVotes_e(String votes_e) {
		this.votes_e = votes_e;
	}

	public String getVotes_f() {
		return votes_f;
	}

	public void setVotes_f(String votes_f) {
		this.votes_f = votes_f;
	}

	public String getVotes_g() {
		return votes_g;
	}

	public void setVotes_g(String votes_g) {
		this.votes_g = votes_g;
	}

	public String getVotes_h() {
		return votes_h;
	}

	public void setVotes_h(String votes_h) {
		this.votes_h = votes_h;
	}

	public String getVotes_i() {
		return votes_i;
	}

	public void setVotes_i(String votes_i) {
		this.votes_i = votes_i;
	}

	public String getVotes_j() {
		return votes_j;
	}

	public void setVotes_j(String votes_j) {
		this.votes_j = votes_j;
	}

}
