package com.fashionsuperman.fs.game.dao.entity.custom;

public class ResGetUserPackageList {
	/**
	 * 背包id
	 */
	private Long packageid;
	/**
	 * 商品分类id
	 */
	private Long catagoryid;
    /**
     * 商品分类名称
     */
    private String catagoryname;
	/**
	 * 商品id
	 */
	private Long commodityid;
	/**
	 * 商品名称
	 */
	private String commodityname;
	/**
	 * 数量
	 */
	private Integer number;
	public Long getPackageid() {
		return packageid;
	}
	public void setPackageid(Long packageid) {
		this.packageid = packageid;
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
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
}
