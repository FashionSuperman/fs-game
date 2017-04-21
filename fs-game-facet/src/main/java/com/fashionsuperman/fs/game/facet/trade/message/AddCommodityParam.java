package com.fashionsuperman.fs.game.facet.trade.message;

import com.fashionsuperman.fs.game.dao.entity.Commodity;

public class AddCommodityParam extends Commodity{
	/**
	 * 商品分类名称,如果不传递商品分类id,需要传递该字段,用于先创建(或者获取)该商品分类
	 */
	private String catagoryname;

	public String getCatagoryname() {
		return catagoryname;
	}

	public void setCatagoryname(String catagoryname) {
		this.catagoryname = catagoryname;
	}
	
	
}
