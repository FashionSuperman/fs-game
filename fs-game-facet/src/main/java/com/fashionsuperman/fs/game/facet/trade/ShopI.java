package com.fashionsuperman.fs.game.facet.trade;

import java.io.IOException;

import com.fashionSuperman.fs.core.common.PageInfo;
import com.fashionSuperman.fs.core.exception.BizException;
import com.fashionsuperman.fs.game.facet.trade.message.MesBuyShopCommodity;
import com.fashionsuperman.fs.game.facet.trade.message.MesGetShopCommodities;

public interface ShopI {
	/**
	 * 获取/查询  商店商品
	 * @param param
	 * @return
	 */
	public PageInfo getShopCommodities(MesGetShopCommodities param) throws BizException;
	
	/**
	 * 购买商店商品
	 * @param param
	 */
	public MesBuyShopCommodity buyShopCommodity(MesBuyShopCommodity param) throws BizException , IOException;
}
