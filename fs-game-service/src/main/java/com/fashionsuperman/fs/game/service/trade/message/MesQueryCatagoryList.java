package com.fashionsuperman.fs.game.service.trade.message;

import com.fashionSuperman.fs.core.common.PageInfo;
/**
 * 查询分类列表参数
 * @author Administrator
 *
 */
public class MesQueryCatagoryList extends PageInfo{
	/**
	 * 分类名称
	 */
	private String catagoryname;

	public String getCatagoryname() {
		return catagoryname;
	}

	public void setCatagoryname(String catagoryname) {
		this.catagoryname = catagoryname;
	}
}
