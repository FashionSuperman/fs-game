package com.fashionsuperman.fs.game.facet.trade;

import com.fashionSuperman.fs.core.common.PageInfo;
import com.fashionsuperman.fs.game.facet.trade.message.MesBuyShopCommodity;
import com.fashionsuperman.fs.game.facet.trade.message.MesGetShopCommodities;

public interface ShopI {
	/**
	 * 获取/查询  商店商品
	 * @param param
	 * @return
	 */
	public PageInfo getShopCommodities(MesGetShopCommodities param);
	
	/**
	 * 购买商店商品
	 * @param param
	 */
	public void buyShopCommodity(MesBuyShopCommodity param);
}
