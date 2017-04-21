package com.fashionsuperman.fs.game.dao.entity.custom;

import com.fashionsuperman.fs.game.dao.entity.Shop;

public class ShopCustom extends Shop{
	/**
	 * 商品名称
	 */
	private String commodityname;
	/**
	 * 商品描述
	 */
	private String commoditydes;
	/**
	 * 商品分类名称
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
	public String getCommoditydes() {
		return commoditydes;
	}
	public void setCommoditydes(String commoditydes) {
		this.commoditydes = commoditydes;
	}
	
	
}
