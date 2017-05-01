package com.fashionsuperman.fs.game.dao.entity.custom;

import com.fashionsuperman.fs.game.dao.entity.Commodity;

/**
 * 获取用户背包返回
 * @author Administrator
 *
 */
public class UserPackageListCustom extends Commodity{
	/**
	 * 数量
	 */
	private Integer number;

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}
	
	
}
