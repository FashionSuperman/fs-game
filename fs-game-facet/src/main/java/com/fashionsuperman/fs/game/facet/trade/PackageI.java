package com.fashionsuperman.fs.game.facet.trade;

import com.fashionSuperman.fs.core.common.PageInfo;
import com.fashionSuperman.fs.core.exception.BizException;
import com.fashionsuperman.fs.game.facet.trade.message.MesGetUserPackageList;

/**
 * 背包接口定义
 * @author Administrator
 *
 */
public interface PackageI {
	/**
	 * 获取用户背包列表
	 * @param param
	 * @return
	 */
	PageInfo getUserPackageList(MesGetUserPackageList param) throws BizException;
}
