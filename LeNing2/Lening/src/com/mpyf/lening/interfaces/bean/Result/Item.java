package com.mpyf.lening.interfaces.bean.Result;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author  ‘Ã‚—°œÓ
 *
 */
public class Item {
	@JsonProperty("PK_Item")
	private String PK_Item;

	public String getPK_Item() {
		return PK_Item;
	}

	public void setPK_Item(String pK_Item) {
		PK_Item = pK_Item;
	}

	@JsonProperty("PK_Que")
	private String PK_Que;

	public String getPK_Que() {
		return PK_Que;
	}

	public void setPK_Que(String pK_Que) {
		PK_Que = pK_Que;
	}

	@JsonProperty("Item_CONTENT")
	private String Item_CONTENT;

	public String getItem_CONTENT() {
		return Item_CONTENT;
	}

	public void setItem_CONTENT(String item_CONTENT) {
		Item_CONTENT = item_CONTENT;
	}

	@JsonProperty("IS_Right")
	private Boolean IS_Right;

	public Boolean getIS_Right() {
		return IS_Right;
	}

	public void setIS_Right(Boolean iS_Right) {
		IS_Right = iS_Right;
	}
	
	

}
