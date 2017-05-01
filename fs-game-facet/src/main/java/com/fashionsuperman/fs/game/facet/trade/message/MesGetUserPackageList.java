package com.fashionsuperman.fs.game.facet.trade.message;

import com.fashionSuperman.fs.core.common.PageInfo;

/**
 * 获取用户背包列表参数
 * @author Administrator
 *
 */
public class MesGetUserPackageList extends PageInfo{
	/**
	 * 用户id
	 */
	private Long userid;
	/**
	 * 商品分类id
	 */
	private Long catagoryid;
	/**
	 * 商品名称
	 */
	private String commodityname;
	public Long getCatagoryid() {
		return catagoryid;
	}
	public void setCatagoryid(Long catagoryid) {
		this.catagoryid = catagoryid;
	}
	public String getCommodityname() {
		return commodityname;
	}
	public void setCommodityname(String commodityname) {
		this.commodityname = commodityname;
	}
	public Long getUserid() {
		return userid;
	}
	public void setUserid(Long userid) {
		this.userid = userid;
	}
	
	
}
