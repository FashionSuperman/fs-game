package com.fashionsuperman.fs.game.facet.trade.commodity;

import com.fashionsuperman.fs.game.dao.entity.Commodity;
import com.fashionsuperman.fs.game.facet.commodity.message.AddCommodityParam;

/**
 * 商品接口
 * @description 
 * @author FashionSuperman
 * @date 2017年3月27日 上午11:16:09
 * @version 1.0
 */
public interface CommodityI {
	/**
	 * 添加商品
	 * @param addCommodityParam
	 */
	void addCommodity(AddCommodityParam addCommodityParam);
	/**
	 * 删除商品
	 * @param commodity
	 * @return
	 */
	int deleteCommodity(Commodity commodity);
}
