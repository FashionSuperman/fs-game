package com.fashionsuperman.fs.game.facet.biz;

import java.io.IOException;

import com.fashionSuperman.fs.core.exception.BizException;
import com.fashionsuperman.fs.game.facet.biz.message.MesApplyWXPay;
import com.fashionsuperman.fs.game.facet.biz.message.MesJudgeCanPlay;
import com.fashionsuperman.fs.game.facet.biz.message.MesSign;
import com.fashionsuperman.fs.game.facet.biz.message.ResApplyWXPay;
import com.fashionsuperman.fs.game.facet.biz.message.ResSign;
import com.fashionsuperman.fs.game.facet.biz.message.StatusCode;

/**
 * 个性化业务,单独接口定义
 * @description 
 * @author FashionSuperman
 * @date 2017年5月2日 上午11:26:38
 * @version 1.0
 */
public interface DogBizServiceI {
	/**
	 * 判断用户是否可以玩游戏(玩游戏道具是否在背包中有余量)
	 * @param mesJudgeCanPlay
	 * @return
	 */
	StatusCode judgeCanPlay(MesJudgeCanPlay mesJudgeCanPlay) throws BizException , IOException;
	/**
	 * 签名 
	 * @param mesSign
	 * @return
	 * @throws BizException
	 */
	public ResSign sign(MesSign mesSign) throws BizException;
	
	/**
	 * 调起微信支付
	 * @param mesApplyWXPay
	 * @return
	 * @throws BizException
	 */
	public ResApplyWXPay applyWXPay(MesApplyWXPay mesApplyWXPay) throws BizException;
}
