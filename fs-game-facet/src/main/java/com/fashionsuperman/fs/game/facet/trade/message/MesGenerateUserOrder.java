package com.fashionsuperman.fs.game.facet.trade.message;

public class MesGenerateUserOrder {
	/**
	 * 用户id
	 */
	private Long userid;
	/**
	 * 商品id  非必须
	 */
	private Long commodityid;
	/**
	 * 数量	非必须
	 */
	private Integer number;
	/**
	 * 备注
	 */
	private String note;
	public Long getUserid() {
		return userid;
	}
	public void setUserid(Long userid) {
		this.userid = userid;
	}
	public Long getCommodityid() {
		return commodityid;
	}
	public void setCommodityid(Long commodityid) {
		this.commodityid = commodityid;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	
	
	
}
