package com.mpyf.lening.interfaces.bean.Parame;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author ¿¼Éú´ð°¸
 *
 */
public class ItemUser extends ParameBase{

	@JsonProperty("PK_Item")
	private String PK_Item;
	
	@JsonProperty("PK_Que")
	private String PK_Que;
	
	@JsonProperty("Item_CONTENT")
	private String Item_CONTENT;

	public String getPK_Item() {
		return PK_Item;
	}

	public void setPK_Item(String pK_Item) {
		PK_Item = pK_Item;
	}

	public String getPK_Que() {
		return PK_Que;
	}

	public void setPK_Que(String pK_Que) {
		PK_Que = pK_Que;
	}

	public String getItem_CONTENT() {
		return Item_CONTENT;
	}

	public void setItem_CONTENT(String item_CONTENT) {
		Item_CONTENT = item_CONTENT;
	}
	
}
