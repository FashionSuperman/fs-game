package com.fashionsuperman.fs.game.service.trade;

import org.springframework.stereotype.Service;
/**
 * 
 * @description 微信支付
 * @author FashionSuperman
 * @date 2017年7月3日 上午10:47:22
 * @version 1.0
 */
@Service
public class WXPayService {
	/**
	 * 微信生成订单接口
	 */
	private String unifiedorderUrl = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	
	public void unifiedorder(){
		
	}
}
