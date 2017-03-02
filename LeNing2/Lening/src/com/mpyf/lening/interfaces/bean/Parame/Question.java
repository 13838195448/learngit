package com.mpyf.lening.interfaces.bean.Parame;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Question extends ParameBase {
	@JsonProperty("pic1")
	@JsonInclude(Include.NON_NULL)
	private byte[] pic1;
	@JsonProperty("pic2")
	@JsonInclude(Include.NON_NULL)
	private byte[] pic2;
	@JsonProperty("pic3")
	@JsonInclude(Include.NON_NULL)
	private byte[] pic3;
	@JsonProperty("pic4")
	@JsonInclude(Include.NON_NULL)
	private byte[] pic4;
	@JsonProperty("pic5")
	@JsonInclude(Include.NON_NULL)
	private byte[] pic5;
	@JsonProperty("pic6")
	@JsonInclude(Include.NON_NULL)
	private byte[] pic6;
	@JsonProperty("pic7")
	@JsonInclude(Include.NON_NULL)
	private byte[] pic7;
	@JsonProperty("pic8")
	@JsonInclude(Include.NON_NULL)
	private byte[] pic8;
	@JsonProperty("pic9")
	@JsonInclude(Include.NON_NULL)
	private byte[] pic9;
	// 图片总数
	@JsonProperty("pic_num")
	private int pic_num;

	// 问题主键
	@JsonProperty("PK_Que")
	private String PK_Que;
	// 提问者
	@JsonProperty("pk_user")
	private int pk_user;
	// 用户名
	@JsonProperty("userName")
	private String userName;
	// 昵称
	@JsonProperty("nickname")
	private String nickname;
	// 真实姓名
	@JsonProperty("trueName")
	private String trueName;
	// 问题内容
	@JsonProperty("QUE_CONTENT")
	private String QUE_CONTENT;
	// 悬赏方式
	@JsonProperty("REWARD_WAY")
	private int REWARD_WAY;
	// 悬赏数
	@JsonProperty("REWARD_Num")
	private int REWARD_Num;
	// 预览数
	@JsonProperty("READ_Num")
	private int READ_Num;
	// 答案数
	@JsonProperty("ans_Num")
	private int ans_Num;
	// 是否采纳
	@JsonProperty("QUE_STATE")
	private int QUE_STATE;
	// 问题时间
	@JsonProperty("queTime")
	private String queTime;

	// 荣誉称号
	@JsonProperty("honor_name")
	private String honor_name;
	// 荣誉称号图片地址
	@JsonProperty("honor_pic")
	private String honor_pic;
	// 是否已收藏[Byte] 0否，1是
	@JsonProperty("isCollection")
	private int isCollection;

	// 标签总数
	@JsonProperty("label_num")
	private int label_num;

	// 标签1Id
	@JsonProperty("label1_id")
	private String label1_id;

	// 标签2Id
	@JsonProperty("label2_id")
	private String label2_id;
	// 标签3Id
	@JsonProperty("label3_id")
	private String label3_id;

	public int getIsCollection() {
		return isCollection;
	}

	public void setIsCollection(int isCollection) {
		this.isCollection = isCollection;
	}

	public int getPic_num() {
		return pic_num;
	}

	public void setPic_num(int pic_num) {
		this.pic_num = pic_num;
	}

	public int getLabel_num() {
		return label_num;
	}

	public void setLabel_num(int label_num) {
		this.label_num = label_num;
	}

	public String getLabel1_id() {
		return label1_id;
	}

	public void setLabel1_id(String label1_id) {
		this.label1_id = label1_id;
	}

	public String getLabel2_id() {
		return label2_id;
	}

	public void setLabel2_id(String label2_id) {
		this.label2_id = label2_id;
	}

	public String getLabel3_id() {
		return label3_id;
	}

	public void setLabel3_id(String label3_id) {
		this.label3_id = label3_id;
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

	public String getPK_Que() {
		return PK_Que;
	}

	public void setPK_Que(String pK_Que) {
		PK_Que = pK_Que;
	}

	public int getPk_user() {
		return pk_user;
	}

	public void setPk_user(int pk_user) {
		this.pk_user = pk_user;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getTrueName() {
		return trueName;
	}

	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}

	public String getQUE_CONTENT() {
		return QUE_CONTENT;
	}

	public void setQUE_CONTENT(String qUE_CONTENT) {
		QUE_CONTENT = qUE_CONTENT;
	}

	public int getREWARD_WAY() {
		return REWARD_WAY;
	}

	public void setREWARD_WAY(int rEWARD_WAY) {
		REWARD_WAY = rEWARD_WAY;
	}

	public int getREWARD_Num() {
		return REWARD_Num;
	}

	public void setREWARD_Num(int rEWARD_Num) {
		REWARD_Num = rEWARD_Num;
	}

	public int getREAD_Num() {
		return READ_Num;
	}

	public void setREAD_Num(int rEAD_Num) {
		READ_Num = rEAD_Num;
	}

	public int getAns_Num() {
		return ans_Num;
	}

	public void setAns_Num(int ans_Num) {
		this.ans_Num = ans_Num;
	}

	public int getQUE_STATE() {
		return QUE_STATE;
	}

	public void setQUE_STATE(int qUE_STATE) {
		QUE_STATE = qUE_STATE;
	}

	public String getQueTime() {
		return queTime;
	}

	public void setQueTime(String queTime) {
		this.queTime = queTime;
	}

	public byte[] getPic1() {
		return pic1;
	}

	public void setPic1(byte[] pic1) {
		this.pic1 = pic1;
	}

	public byte[] getPic2() {
		return pic2;
	}

	public void setPic2(byte[] pic2) {
		this.pic2 = pic2;
	}

	public byte[] getPic3() {
		return pic3;
	}

	public void setPic3(byte[] pic3) {
		this.pic3 = pic3;
	}

	public byte[] getPic4() {
		return pic4;
	}

	public void setPic4(byte[] pic4) {
		this.pic4 = pic4;
	}

	public byte[] getPic5() {
		return pic5;
	}

	public void setPic5(byte[] pic5) {
		this.pic5 = pic5;
	}

	public byte[] getPic6() {
		return pic6;
	}

	public void setPic6(byte[] pic6) {
		this.pic6 = pic6;
	}

	public byte[] getPic7() {
		return pic7;
	}

	public void setPic7(byte[] pic7) {
		this.pic7 = pic7;
	}

	public byte[] getPic8() {
		return pic8;
	}

	public void setPic8(byte[] pic8) {
		this.pic8 = pic8;
	}

	public byte[] getPic9() {
		return pic9;
	}

	public void setPic9(byte[] pic9) {
		this.pic9 = pic9;
	}

}
