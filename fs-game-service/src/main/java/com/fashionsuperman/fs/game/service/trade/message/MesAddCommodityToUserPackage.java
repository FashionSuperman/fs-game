package com.fashionsuperman.fs.game.service.trade.message;

public class MesAddCommodityToUserPackage {
	/**
	 * 用户id
	 */
	private Long userid;
	/**
	 * 商品id
	 */
	private Long commodityid;
	/**
	 * 数量
	 */
	private Integer number;
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
	
	
}
