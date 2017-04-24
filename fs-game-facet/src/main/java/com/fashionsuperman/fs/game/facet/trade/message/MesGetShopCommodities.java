package com.fashionsuperman.fs.game.facet.trade.message;

import com.fashionSuperman.fs.core.common.PageInfo;

public class MesGetShopCommodities extends PageInfo{
	/**
	 * 商品id
	 */
	private Long commodityid;
	/**
	 * 商品名称
	 */
	private String commodityname;
	/**
	 * 商品分类id
	 */
	private Long catagoryid;
	/**
	 * 商品分类名称
	 */
	private String catagoryname;
	public Long getCommodityid() {
		return commodityid;
	}
	public void setCommodityid(Long commodityid) {
		this.commodityid = commodityid;
	}
	public String getCommodityname() {
		return commodityname;
	}
	public void setCommodityname(String commodityname) {
		this.commodityname = commodityname;
	}
	public Long getCatagoryid() {
		return catagoryid;
	}
	public void setCatagoryid(Long catagoryid) {
		this.catagoryid = catagoryid;
	}
	public String getCatagoryname() {
		return catagoryname;
	}
	public void setCatagoryname(String catagoryname) {
		this.catagoryname = catagoryname;
	}
	
	
}
