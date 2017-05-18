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
import com.fashionsuperman.fs.game.facet.biz.message.MesJudgeCanPlay;
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
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("userid", userid);
		paramMap.put("commodityid", canPlayCommodityId);
		
		
		Package packageUser = dogBizService.getUserFooCommodityNum(paramMap);
		
		if(packageUser != null && packageUser.getNumber() != null && packageUser.getNumber() > 0){
			result.setStatus("1");
		}
		
		return result;
	}

}
