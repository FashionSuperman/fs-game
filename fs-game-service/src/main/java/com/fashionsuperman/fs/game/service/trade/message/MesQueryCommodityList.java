package com.fashionsuperman.fs.game.service.trade.message;

import com.fashionSuperman.fs.core.common.PageInfo;

public class MesQueryCommodityList extends PageInfo{
	/**
	 * 商品名称
	 */
	private String commodityname;
	/**
	 * 商品分类
	 */
	private String catagoryname;
	public String getCommodityname() {
		return commodityname;
	}
	public void setCommodityname(String commodityname) {
		this.commodityname = commodityname;
	}
	public String getCatagoryname() {
		return catagoryname;
	}
	public void setCatagoryname(String catagoryname) {
		this.catagoryname = catagoryname;
	}
	
	
	
}
