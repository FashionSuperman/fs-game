package com.fashionsuperman.fs.game.dubboxService.biz;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;
import com.fashionSuperman.fs.core.exception.BizException;
import com.fashionSuperman.fs.core.util.JedisUtil;
import com.fashionsuperman.fs.game.dao.entity.Package;
import com.fashionsuperman.fs.game.dao.entity.User;
import com.fashionsuperman.fs.game.dubboxService.common.DubboxCookieComponent;
import com.fashionsuperman.fs.game.facet.biz.DogBizServiceI;
import com.fashionsuperman.fs.game.facet.biz.message.MesApplyWXPay;
import com.fashionsuperman.fs.game.facet.biz.message.MesJudgeCanPlay;
import com.fashionsuperman.fs.game.facet.biz.message.MesSign;
import com.fashionsuperman.fs.game.facet.biz.message.ResApplyWXPay;
import com.fashionsuperman.fs.game.facet.biz.message.ResSign;
import com.fashionsuperman.fs.game.facet.biz.message.StatusCode;
import com.fashionsuperman.fs.game.service.biz.DogBizService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Path("/DogBiz")
@Produces(ContentType.APPLICATION_JSON_UTF_8)
@Service("DogBizServiceX")
public class DogBizServiceX implements DogBizServiceI{
	@Autowired
	private DogBizService dogBizService;
	@Value("#{utilProperties.canPlayCommodityId}")
	private Long canPlayCommodityId;
	@Value("#{utilProperties.permanentCommodityId}")
	private Long permanentCommodityId;
	@Autowired
	private JedisUtil jedisUtil;
	@Autowired
	private DubboxCookieComponent dubboxCookieComponent;
	
	/**
	 * 判断用户是否可以继续游戏
	 * 0失败
	 * 1成功
	 */
	@POST
	@Path("/judgeCanPlay")
	@Consumes(MediaType.APPLICATION_JSON)
	@Override
	public StatusCode judgeCanPlay(MesJudgeCanPlay mesJudgeCanPlay) {
		StatusCode result = new StatusCode();
		
		if(mesJudgeCanPlay == null){
			throw new BizException(com.fashionSuperman.fs.core.constant.StatusCode.FAILURE_AUTHENTICATE, "请求参数不能为空");
		}
		Long userid = mesJudgeCanPlay.getUserid();
		if(userid == null || userid < 0){
			throw new BizException(com.fashionSuperman.fs.core.constant.StatusCode.FAILURE_AUTHENTICATE, "userid不能为空");
		}
		
		//TODO
		//判断是否登录  如果已登录按登录用户的逻辑判断,如果未登录,按未登录逻辑判断(每12小时3次)
		User user = dubboxCookieComponent.getLoginUser();
		if(user != null){//已登录
			
		}else{//未登录
			
		}
		
		
		//以下代码会在用户登录 并且用户免费试玩次数耗尽之后才会执行
		
		Map<String, Object> paramMap;
		Package packageUser;
		
		//首先判断是否有永久道具
		paramMap = new HashMap<>();
		paramMap.put("userid", userid);
		paramMap.put("commodityid", permanentCommodityId);
		dogBizService.getUserFooCommodityNum(paramMap);
		packageUser = dogBizService.getUserFooCommodityNum(paramMap);
		if(packageUser != null && packageUser.getNumber() != null && packageUser.getNumber() > 0){
			result.setStatus("1");
			//如果有永久道具,直接返回
			return result;
		}
		
		
		//没有永久道具,判断分次道具
		paramMap = new HashMap<>();
		paramMap.put("userid", userid);
		paramMap.put("commodityid", canPlayCommodityId);
		
		
		packageUser = dogBizService.getUserFooCommodityNum(paramMap);
		
		if(packageUser != null && packageUser.getNumber() != null && packageUser.getNumber() > 0){
			result.setStatus("1");
		}
		
		return result;
	}
	
	
	/**
	 * 签名
	 */
	@POST
	@Path("/sign")
	@Consumes(MediaType.APPLICATION_JSON)
	@Override
	public ResSign sign(MesSign mesSign){
		return dogBizService.sign(mesSign);
	}

	/**
	 * 调起微信支付
	 */
	@POST
	@Path("/applyWXPay")
	@Consumes(MediaType.APPLICATION_JSON)
	@Override
	public ResApplyWXPay applyWXPay(MesApplyWXPay mesApplyWXPay){
		
		HttpServletRequest httpServletRequest = (HttpServletRequest) RpcContext.getContext().getRequest();
		
		String ip = httpServletRequest.getHeader("x-forwarded-for");
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
	        ip = httpServletRequest.getHeader("Proxy-Client-IP");
	    }
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
	        ip = httpServletRequest.getHeader("WL-Proxy-Client-IP");
	    }
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
	        ip = httpServletRequest.getRemoteAddr();
	    }
	    ip = ip.equals("0:0:0:0:0:0:0:1")?"127.0.0.1":ip;
		
		Cookie[] cookies = httpServletRequest.getCookies();
		Cookie cookie = null;
		if (cookies == null || cookies.length == 0) {
			throw new BizException(com.fashionSuperman.fs.core.constant.StatusCode.FAILURE_AUTHENTICATE, "未登录");
		}
		for (Cookie c : cookies) {
			if ("sessionId".equals(c.getName())) {
				cookie = c;
				break;
			}
		}
		
		if(cookie == null){
			throw new BizException(com.fashionSuperman.fs.core.constant.StatusCode.FAILURE_AUTHENTICATE, "未登录");
		}
		
		String sessionId = cookie.getValue();
		
		//从redis查询该用户
		String userInRedis = this.jedisUtil.STRINGS.get(sessionId);
//		String userInRedis = this.jedisUtil.STRINGS.get("oR8r7t5-S45elI3FJjX17v86sYcU");
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		
		User user = null;
		try {
			user = mapper.readValue(userInRedis, User.class);
		} catch (IOException e) {
			throw new BizException(com.fashionSuperman.fs.core.constant.StatusCode.FAILURE_AUTHENTICATE, "未登录");
		}
		
		mesApplyWXPay.setOpenid(user.getForeighid());
		
		ResApplyWXPay result = new ResApplyWXPay();
		result = dogBizService.applyWXPay(mesApplyWXPay);
		return result;
	}
}
