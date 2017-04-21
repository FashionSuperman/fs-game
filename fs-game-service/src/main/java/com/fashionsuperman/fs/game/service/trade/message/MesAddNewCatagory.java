package com.fashionsuperman.fs.game.service.trade.message;
/**
 * 添加分类参数
 * @author Administrator
 *
 */
public class MesAddNewCatagory {
	/**
	 * 分类名称
	 */
	private String catagoryname;
	
	/**
	 * 传递该参数则进行更新
	 */
	private Long catagoryId;
	
	
	

	public Long getCatagoryId() {
		return catagoryId;
	}

	public void setCatagoryId(Long catagoryId) {
		this.catagoryId = catagoryId;
	}

	public String getCatagoryname() {
		return catagoryname;
	}

	public void setCatagoryname(String catagoryname) {
		this.catagoryname = catagoryname;
	}
	
	
}
