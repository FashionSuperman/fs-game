package com.fashionsuperman.fs.game.service.trade;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fashionsuperman.fs.game.service.common.UtilConstant;
import com.fashionsuperman.fs.game.service.trade.message.MesUnifiedorder;
/**
 * 
 * @description 微信支付
 * @author FashionSuperman
 * @date 2017年7月3日 上午10:47:22
 * @version 1.0
 */
@Service
public class WXPayService {
	@Autowired
	private UtilConstant utilConstant;
	public void unifiedorder(){
		/**
		 * 微信生成订单接口
		 */
		String unifiedorderUrl = utilConstant.unifiedorderUrl;
		
		MesUnifiedorder mesUnifiedorder = new MesUnifiedorder();
		
		mesUnifiedorder.setAppid(utilConstant.appid);
		mesUnifiedorder.setMch_id(utilConstant.mch_id);
		mesUnifiedorder.setDevice_info("WEB");
		String nonce_str = UUID.randomUUID().toString().replace("-", "");
		mesUnifiedorder.setNonce_str(nonce_str);
		
		//设置商品描述
		String body = "";
		
		
		
		//设置签名类型为md5
		mesUnifiedorder.setSign_type("MD5");
		//TODO 最后设置签名
		String sign;
		
		
		
	}
}
