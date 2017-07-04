package com.fashionsuperman.fs.game.dubboxService.biz;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;
import com.fashionSuperman.fs.core.exception.BizException;
import com.fashionsuperman.fs.game.dao.entity.Package;
import com.fashionsuperman.fs.game.facet.biz.DogBizServiceI;
import com.fashionsuperman.fs.game.facet.biz.message.MesApplyWXPay;
import com.fashionsuperman.fs.game.facet.biz.message.MesJudgeCanPlay;
import com.fashionsuperman.fs.game.facet.biz.message.MesSign;
import com.fashionsuperman.fs.game.facet.biz.message.ResApplyWXPay;
import com.fashionsuperman.fs.game.facet.biz.message.ResSign;
import com.fashionsuperman.fs.game.facet.biz.message.StatusCode;
import com.fashionsuperman.fs.game.service.biz.DogBizService;

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
		ResApplyWXPay result = new ResApplyWXPay();
		result = dogBizService.applyWXPay(mesApplyWXPay);
		return result;
	}
}
