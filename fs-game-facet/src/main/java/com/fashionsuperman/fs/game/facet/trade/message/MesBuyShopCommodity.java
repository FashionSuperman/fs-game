package com.fashionsuperman.fs.game.facet.trade.message;
/**
 * 购买商店商品参数
 * @author Administrator
 *
 */
public class MesBuyShopCommodity {
	private Long userid;
	/**
	 * 商店商品id
	 */
	private Long shopitemid;
	/**
	 * 购买数量
	 */
	private Integer number;
	public Long getUserid() {
		return userid;
	}
	public void setUserid(Long userid) {
		this.userid = userid;
	}
	public Long getShopitemid() {
		return shopitemid;
	}
	public void setShopitemid(Long shopitemid) {
		this.shopitemid = shopitemid;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	
	
}
