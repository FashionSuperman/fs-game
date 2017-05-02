package com.fashionsuperman.fs.game.dubboxService.biz;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.fashionSuperman.fs.core.exception.BizException;
import com.fashionsuperman.fs.game.facet.biz.DogBizServiceI;
import com.fashionsuperman.fs.game.facet.biz.message.MesJudgeCanPlay;
import com.fashionsuperman.fs.game.facet.biz.message.StatusCode;
import com.fashionsuperman.fs.game.service.biz.DogBizService;

public class DogBizServiceX implements DogBizServiceI{
	@Autowired
	private DogBizService dogBizService;
	@Value("#{utilProperties.canPlayCommodityId}")
	private Long canPlayCommodityId;
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
		
		
		int num = dogBizService.getUserFooCommodityNum(paramMap);
		
		if(num > 0){
			result.setStatus("1");
		}
		
		return result;
	}

}
