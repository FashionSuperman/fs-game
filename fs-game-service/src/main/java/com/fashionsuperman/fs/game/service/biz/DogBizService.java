package com.fashionsuperman.fs.game.service.biz;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fashionSuperman.fs.core.constant.StatusCode;
import com.fashionSuperman.fs.core.exception.BizException;
import com.fashionSuperman.fs.core.util.HttpClientUtil;
import com.fashionSuperman.fs.core.util.JedisUtil;
import com.fashionSuperman.fs.core.util.StringUtil;
import com.fashionsuperman.fs.game.dao.entity.UserShare;
import com.fashionsuperman.fs.game.dao.mapper.UserPackageMapper;
import com.fashionsuperman.fs.game.dao.mapper.UserShareMapper;
import com.fashionsuperman.fs.game.facet.biz.message.GetticketUrlJson;
import com.fashionsuperman.fs.game.facet.biz.message.GetwxInterfaceTokenUrlJson;
import com.fashionsuperman.fs.game.facet.biz.message.MesApplyWXPay;
import com.fashionsuperman.fs.game.facet.biz.message.MesShareSuccess;
import com.fashionsuperman.fs.game.facet.biz.message.MesSign;
import com.fashionsuperman.fs.game.facet.biz.message.ResApplyWXPay;
import com.fashionsuperman.fs.game.facet.biz.message.ResGetWxConfigParam;
import com.fashionsuperman.fs.game.facet.biz.message.ResShareSuccess;
import com.fashionsuperman.fs.game.facet.biz.message.ResSign;
import com.fashionsuperman.fs.game.facet.biz.message.WxApiEncode;
import com.fashionsuperman.fs.game.service.common.UtilConstant;
import com.fashionsuperman.fs.game.service.common.WXSignUtil;
import com.fashionsuperman.fs.game.service.trade.PackageService;
import com.fashionsuperman.fs.game.service.trade.WXPayService;
import com.fashionsuperman.fs.game.service.trade.message.MesAddCommodityToUserPackage;
import com.fashionsuperman.fs.game.service.trade.message.MesUnifiedorderInner;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
@Service
public class DogBizService {
	@Autowired
	private UserPackageMapper userPackageMapper;
	@Autowired
	private UtilConstant UtilConstant;
	
	@Autowired
	private WXPayService wxPayService;
	@Autowired
	private UserShareMapper userShareMapper;
	@Autowired
	private PackageService packageService;
	@Autowired
	private JedisUtil jedisUtil;
	private Logger Logger = LogManager.getLogger(DogBizService.class);
	
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
		param.setOrderid(mesApplyWXPay.getOrderid());
		
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
	
	/**
	 * 获取调用微信config接口的参数
	 * @param param
	 * @return
	 */
	public ResGetWxConfigParam getWxConfigParam(){
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		
		//尝试从redis 获取jsapi_ticket
		String jsapi_ticket = this.jedisUtil.STRINGS.get("jsapi_ticket");
		if(StringUtil.isNullOrEmpty(jsapi_ticket)){
			//获取accessToken
			//尝试从redis获取微信接口调用的token
			String token = this.jedisUtil.STRINGS.get("wxapitoken");
			if(StringUtil.isNullOrEmpty(token)){//已过期 重新获取
				
				//获取accesstoken
				String getwxInterfaceTokenUrl = UtilConstant.getwxInterfaceToken;
				getwxInterfaceTokenUrl = String.format(getwxInterfaceTokenUrl, UtilConstant.appid,UtilConstant.secret);
				String getwxInterfaceTokenUrlJson = HttpClientUtil.doGet(getwxInterfaceTokenUrl);
				String accessToken = null;
				try {
					GetwxInterfaceTokenUrlJson getwxInterfaceTokenUrlJson2 = objectMapper.readValue(getwxInterfaceTokenUrlJson, GetwxInterfaceTokenUrlJson.class);
					accessToken = getwxInterfaceTokenUrlJson2.getAccess_token();
					token = accessToken;
					//保存到redis
					this.jedisUtil.STRINGS.set("wxapitoken", accessToken);
					this.jedisUtil.KEYS.expired("wxapitoken", 7100);
				} catch (IOException e1) {
					
				}
			}else{
				Logger.debug("wxapitoken redis has!!");
			}
			
			//用拿到的access_token 采用http GET方式请求获得jsapi_ticket
			String getticketUrl = UtilConstant.getticketUrl;
			getticketUrl = String.format(getticketUrl, token);
			String getticketUrlJson = HttpClientUtil.doGet(getticketUrl);
			try {
				GetticketUrlJson getticketUrlJson2 = objectMapper.readValue(getticketUrlJson, GetticketUrlJson.class);
				jsapi_ticket = getticketUrlJson2.getTicket();
				
				//保存到redis
				this.jedisUtil.STRINGS.set("jsapi_ticket", jsapi_ticket);
				this.jedisUtil.KEYS.expired("jsapi_ticket", 7100);
				
			} catch (IOException e) {
			}
			
			
		}else{
			Logger.debug("jsapi_ticket redis has!!");
		}
		
		
		
		ResGetWxConfigParam result = new ResGetWxConfigParam();
		result.setAppId(UtilConstant.appid);
		result.setTimestamp(System.currentTimeMillis() + "");
		String nonce_str = UUID.randomUUID().toString().replace("-", "");
		result.setNonceStr(nonce_str);
		List<String> jsApiList = new ArrayList<>();
		jsApiList.add("onMenuShareTimeline");//分享到朋友圈接口
		jsApiList.add("onMenuShareAppMessage");//分享给朋友接口4
		result.setJsApiList(jsApiList);
		//生成签名
		String sign = null;
//		try {
//			sign = WXSignUtil.sign(result, UtilConstant.wxkey);
//		} catch (IllegalArgumentException | IllegalAccessException e) {
//			throw new BizException(StatusCode.FAILURE_AUTHENTICATE, "生成微信签名失败");
//		}
		WxApiEncode wxApiEncode = new WxApiEncode();
		wxApiEncode.setNoncestr(nonce_str);
		wxApiEncode.setTimestamp(result.getTimestamp());
		wxApiEncode.setUrl(UtilConstant.indexUrl);
		wxApiEncode.setJsapi_ticket(jsapi_ticket);
		try {
			sign = WXSignUtil.signSha1(wxApiEncode);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		
		result.setSignature(sign);
		
		return result;
	}

	public ResShareSuccess shareSuccess(MesShareSuccess param) {
		ResShareSuccess result = new ResShareSuccess();
		
		result.setStatus("2");//默认失败
		
		
		
		//判断用户是否是第一次分享,如果是,用户背包添加奖励,如果不是跳过
		UserShare userShare = userShareMapper.selectByPrimaryKey(param.getUserId());
		if(userShare != null){//用户分享过
			result.setStatus("1");
			return result;
		}
		
		
		//用户背包添加奖励
		MesAddCommodityToUserPackage mesAddCommodityToUserPackage = new MesAddCommodityToUserPackage();
		mesAddCommodityToUserPackage.setUserid(param.getUserId());
		mesAddCommodityToUserPackage.setCommodityid(UtilConstant.canPlayCommodityId);
		mesAddCommodityToUserPackage.setNumber(UtilConstant.shareAwardNum);
		
		packageService.addCommodityToUserPackage(mesAddCommodityToUserPackage);
		//记录用户已经分享
		userShare = new UserShare();
		userShare.setFlag(param.getFlag());
		userShare.setUserid(param.getUserId());
		userShareMapper.insert(userShare);
		
		
		result.setStatus("1");
		
		return result;
	}
}
