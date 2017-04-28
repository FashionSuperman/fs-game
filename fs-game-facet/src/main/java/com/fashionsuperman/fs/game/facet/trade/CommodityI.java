package com.fashionsuperman.fs.game.facet.trade;

import com.fashionSuperman.fs.core.exception.BizException;
import com.fashionsuperman.fs.game.dao.entity.Commodity;
import com.fashionsuperman.fs.game.facet.trade.message.AddCommodityParam;

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
	AddCommodityParam addCommodity(AddCommodityParam addCommodityParam) throws BizException;
	/**
	 * 删除商品
	 * @param commodity
	 * @return
	 */
	int deleteCommodity(Commodity commodity) throws BizException;
}
