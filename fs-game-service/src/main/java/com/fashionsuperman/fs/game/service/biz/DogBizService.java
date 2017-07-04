package com.fashionsuperman.fs.game.service.biz;

import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fashionSuperman.fs.core.constant.StatusCode;
import com.fashionSuperman.fs.core.exception.BizException;
import com.fashionsuperman.fs.game.dao.mapper.UserPackageMapper;
import com.fashionsuperman.fs.game.facet.biz.message.MesApplyWXPay;
import com.fashionsuperman.fs.game.facet.biz.message.MesSign;
import com.fashionsuperman.fs.game.facet.biz.message.ResApplyWXPay;
import com.fashionsuperman.fs.game.facet.biz.message.ResSign;
import com.fashionsuperman.fs.game.service.common.UtilConstant;
import com.fashionsuperman.fs.game.service.common.WXSignUtil;
import com.fashionsuperman.fs.game.service.trade.WXPayService;
import com.fashionsuperman.fs.game.service.trade.message.MesUnifiedorderInner;
@Service
public class DogBizService {
	@Autowired
	private UserPackageMapper userPackageMapper;
	@Autowired
	private UtilConstant UtilConstant;
	
	@Autowired
	private WXPayService wxPayService;
	
	/**
	 * 获取用户背包中某件商品的数量
	 * @param paramMap
	 */
	public com.fashionsuperman.fs.game.dao.entity.Package getUserFooCommodityNum(Map<String, Object> paramMap){
		return userPackageMapper.getUserFooCommodityNum(paramMap);
	}
	
	/**
	 * 签名
	 * @param mesSign
	 * @return
	 */
	public ResSign sign(MesSign mesSign){
		ResSign result = new ResSign();
		String sign = "";
		try {
			sign = WXSignUtil.sign(mesSign, UtilConstant.wxkey);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new BizException(StatusCode.FAILURE_AUTHENTICATE, "签名失败");
		}
		result.setSignString(sign);
		
		return result;
	}
	
	/**
	 * 微信调起支付
	 * @param mesApplyWXPay
	 * @return
	 */
	public ResApplyWXPay applyWXPay(MesApplyWXPay mesApplyWXPay){
		ResApplyWXPay result = new ResApplyWXPay();
		
		//1生成微信订单
		MesUnifiedorderInner param = new MesUnifiedorderInner();
		
		param.setOpenid(mesApplyWXPay.getOpenid());
		param.setSpbill_create_ip(mesApplyWXPay.getSpbill_create_ip());
		param.setTotal_fee(mesApplyWXPay.getTotal_fee());
		
		String prepay_id = null;
		try {
			prepay_id = wxPayService.unifiedorder(param);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new BizException(StatusCode.FAILURE_AUTHENTICATE, "生成微信订单失败");
		}
		
		//2生成调起微信支付的参数以及签名返还端
		result.setAppId(UtilConstant.appid);
		result.setBackage("prepay_id=" + prepay_id);
		String nonce_str = UUID.randomUUID().toString().replace("-", "");
		result.setNonceStr(nonce_str);
		result.setSignType("MD5");
		result.setTimeStamp(System.currentTimeMillis()+"");
		
		//生成签名
		String sign = null;
		try {
			sign = WXSignUtil.sign(result, UtilConstant.wxkey);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new BizException(StatusCode.FAILURE_AUTHENTICATE, "生成微信签名失败");
		}
		
		result.setPaySign(sign);
		
		return result;
	}
}
