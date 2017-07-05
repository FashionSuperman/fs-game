package com.fashionsuperman.fs.game.facet.trade;

import com.fashionSuperman.fs.core.exception.BizException;
import com.fashionsuperman.fs.game.facet.trade.message.MesGenerateUserOrder;
import com.fashionsuperman.fs.game.facet.trade.message.ResGenerateUserOrder;

public interface OrderI {
	public ResGenerateUserOrder generateUserOrder(MesGenerateUserOrder mesGenerateUserOrder) throws BizException;
}
