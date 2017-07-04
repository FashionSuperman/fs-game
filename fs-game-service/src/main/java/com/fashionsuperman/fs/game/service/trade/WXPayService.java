package com.fashionsuperman.fs.game.service.trade;

import java.util.UUID;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fashionSuperman.fs.core.constant.StatusCode;
import com.fashionSuperman.fs.core.exception.BizException;
import com.fashionSuperman.fs.core.util.HttpClientUtil;
import com.fashionSuperman.fs.core.util.XMLUtil;
import com.fashionsuperman.fs.game.service.common.OrderNoUtil;
import com.fashionsuperman.fs.game.service.common.UtilConstant;
import com.fashionsuperman.fs.game.service.common.WXSignUtil;
import com.fashionsuperman.fs.game.service.trade.message.MesUnifiedorder;
import com.fashionsuperman.fs.game.service.trade.message.MesUnifiedorderInner;
import com.fashionsuperman.fs.game.service.trade.message.ResUnifiedorder;
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
	
	private Logger logger = LogManager.getLogger(WXPayService.class);
	/**
	 * 微信内部生成订单服务   返回微信内部订单
	 * @param param
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public String unifiedorder(MesUnifiedorderInner param) throws IllegalArgumentException, IllegalAccessException{
		/**
		 * 预支付订单id   后续支付操作需要
		 */
		String prepay_id;
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
		String body = "会易通-软件";
		mesUnifiedorder.setBody(body);
		
		//生成商户订单号
		String out_trade_no = OrderNoUtil.generateOrderNo();
		mesUnifiedorder.setOut_trade_no(out_trade_no);
		
		//设置  标价金额 	
		mesUnifiedorder.setTotal_fee(param.getTotal_fee());
		
		//设置 用户端ip
		mesUnifiedorder.setSpbill_create_ip(param.getSpbill_create_ip());
		
		//设置 通知回调地址
		mesUnifiedorder.setNotify_url(utilConstant.notify_url);
		
		//设置 交易类型
		mesUnifiedorder.setTrade_type("JSAPI");
		
		//设置用户标志
		mesUnifiedorder.setOpenid(param.getOpenid());
		
		
		//设置签名类型为md5
		mesUnifiedorder.setSign_type("MD5");
		//最后设置签名
		String sign = WXSignUtil.sign(mesUnifiedorder, utilConstant.wxkey);
		
		mesUnifiedorder.setSign(sign);
		
		//序列化成xml请求微信接口 生成微信订单
		String xml = XMLUtil.convertToXml(mesUnifiedorder).replace("&lt;", "<").replace("&gt;", ">");
		
		String resdata = HttpClientUtil.doPostXml(unifiedorderUrl, xml);
		
		//将返回结果反序列化
		try {
			ResUnifiedorder resUnifiedorder = (ResUnifiedorder) XMLUtil.convertXmlStrToObject(ResUnifiedorder.class, resdata);
			
			if("SUCCESS".equals(resUnifiedorder.getReturn_code())){//通信成功
				//判断业务是否成功
				if("SUCCESS".equals(resUnifiedorder.getResult_code())){//业务成功
					prepay_id = resUnifiedorder.getPrepay_id();
				}else{
					logger.error("Result_code== " + resUnifiedorder.getResult_code() + " err_code==" + resUnifiedorder.getErr_code() + " err_code_des==" + resUnifiedorder.getErr_code_des());
					throw new BizException(StatusCode.FAILURE_AUTHENTICATE, "请求支付失败");
				}
			}else{
				logger.error(resUnifiedorder.getReturn_code() + "==" + resUnifiedorder.getReturn_msg());
				throw new BizException(StatusCode.FAILURE_AUTHENTICATE, "请求支付失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			throw new BizException(StatusCode.FAILURE_AUTHENTICATE, "请求支付失败");
		}
		return prepay_id;
	}
	
	
	
	
	
	
}
