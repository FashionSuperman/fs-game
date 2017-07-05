package com.fashionsuperman.fs.game.facet.trade;

import com.fashionsuperman.fs.game.facet.trade.message.MesPayCallback;
import com.fashionsuperman.fs.game.facet.trade.message.ResPayCallback;

/**
 * 
 * @description 微信支付回调
 * @author FashionSuperman
 * @date 2017年7月5日 下午3:01:15
 * @version 1.0
 */
public interface WXPayI {
	ResPayCallback payCallback(MesPayCallback mesPayCallback);
}
